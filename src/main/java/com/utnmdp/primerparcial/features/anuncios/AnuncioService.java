package com.utnmdp.primerparcial.features.anuncios;

import com.utnmdp.primerparcial.common.exceptions.RecursoNoEncontradoException;
import com.utnmdp.primerparcial.features.anunciantes.AnuncianteEntity;
import com.utnmdp.primerparcial.features.anunciantes.AnuncianteRepository;
import com.utnmdp.primerparcial.features.anunciantes.excepciones.AnuncianteInactivoException;
import com.utnmdp.primerparcial.features.anuncios.dtos.AnuncioRequestDTO;
import com.utnmdp.primerparcial.features.anuncios.dtos.AnuncioResponseDTO;
import com.utnmdp.primerparcial.features.anuncios.dtos.CancelacionDTO;
import com.utnmdp.primerparcial.features.anuncios.dtos.GastoRequestDTO;
import com.utnmdp.primerparcial.features.anuncios.excepciones.AnuncioCanceladoException;
import com.utnmdp.primerparcial.features.anuncios.excepciones.CampanaFinalizadaException;
import com.utnmdp.primerparcial.features.anuncios.excepciones.DistribucionGastosNoDisponibleException;
import com.utnmdp.primerparcial.features.anuncios.excepciones.LimiteOverbudgetSuperadoException;
import com.utnmdp.primerparcial.features.anuncios.mapper.AnuncioMapper;
import com.utnmdp.primerparcial.features.campanas.CampanaEntity;
import com.utnmdp.primerparcial.features.campanas.CampanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnuncioService {
    private static final double OVERBUDGET_FACTOR = 1.35;
    private static final double GASTO_MAX_FACTOR = 1.30;
    private static final double REEMBOLSO_VIP = 1.0;
    private static final double REEMBOLSO_REGULAR = 0.80;

    private final AnuncioRepository anuncioRepository;
    private final AnuncianteRepository anuncianteRepository;
    private final CampanaRepository campanaRepository;
    private final AnuncioMapper anuncioMapper;

    @Transactional
    public AnuncioResponseDTO registrar(AnuncioRequestDTO dto) {
        AnuncianteEntity anunciante = anuncianteRepository.findById(dto.getIdAnunciante())
                .orElseThrow(() -> new RecursoNoEncontradoException("Anunciante no encontrado con ID: " + dto.getIdAnunciante()));

        //El anuncio deberá pertenecer a un anunciante que este activo
        if (!anunciante.getActivo()) {
            throw new AnuncianteInactivoException("El anunciante no esta activo");
        }

        CampanaEntity campana = campanaRepository.findById(dto.getIdCampana())
                .orElseThrow(() -> new RecursoNoEncontradoException("Campaña no encontrada con ID: " + dto.getIdCampana()));

        //campaña que no haya alcanzado su fecha de finalización
        if(campana.getFechaFin().isBefore(LocalDateTime.now())) {
            throw new CampanaFinalizadaException("La campaña ha finalizado");
        }

        //Deberá validar que no se supere el límite de presupuesto estipulado en el
        //apartado de Reglas de Negocio
        validarLimiteOverbudget(campana, dto.getInversionEstimada());

        AnuncioEntity anuncio = anuncioMapper.toEntity(dto);
        //aca esta el quilombo de no usar pre-persist
        anuncio.setCodigoAnuncio(UUID.randomUUID().toString());
        anuncio.setFechaCreacion(LocalDateTime.now());
        anuncio.setInversionTotal(0.0);
        anuncio.setEstado(EstadoAnuncio.PENDIENTE);

        return anuncioMapper.toDto(anuncioRepository.save(anuncio));

    }

    @Transactional
    public AnuncioResponseDTO cargarGastos(Long id, GastoRequestDTO dto) {
        AnuncioEntity anuncio = anuncioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Anuncio no encontrado con id: " + id));

        //Si el anuncio se encuentra cancelado, esta operación debe fallar.
        if (anuncio.getEstado() == EstadoAnuncio.CANCELADO) {
            throw new AnuncioCanceladoException("No se puede cargar un gasto a un anuncio cancelado");
        }

        double nuevaInversionTotal = anuncio.getInversionTotal() + dto.getMonto();
        double limiteMaximo = anuncio.getInversionEstimada() * GASTO_MAX_FACTOR;

        //control del limite del 130% y redistribucion
        if (nuevaInversionTotal > limiteMaximo) {
            boolean pudoRedistribuir = intentarRedistribucion(anuncio);

            if (!pudoRedistribuir) {
                throw new DistribucionGastosNoDisponibleException("El gasto excede el limite y no existen anuncios regulares pendientes para redistribuir");
            }

            // Si pudo redistribuir, la inversión estimada del anuncio ya aumento
            // volvemos a calcular el limite maximo
            limiteMaximo = anuncio.getInversionEstimada() * GASTO_MAX_FACTOR;

            // reintentamos validar el limite
            if (nuevaInversionTotal > limiteMaximo) {
                throw new DistribucionGastosNoDisponibleException("Se redistribuyó saldo, pero el gasto aún excede el nuevo límite permitido");
            }
        }

        // Llegamos con el gasto ya validado, etnonces cambiamos el estado
        if (anuncio.getEstado() == EstadoAnuncio.PENDIENTE) {
            anuncio.setEstado(EstadoAnuncio.EN_CURSO);
        }

        // Actualizamos el total
        anuncio.setInversionTotal(nuevaInversionTotal);

        // Cierre autoamticc si supera la estimacion original (pero sin pasar el 130%)
        if (anuncio.getInversionTotal() > anuncio.getInversionEstimada()) {
            anuncio.setEstado(EstadoAnuncio.FINALIZADO);
        }

        return anuncioMapper.toDto(anuncioRepository.save(anuncio));
    }

    // Sedeberá retornar un CancelacionDTO, que contenga la información del
    //anuncio cancelado, asi como el monto que fue reembolsado.
    @Transactional
    public CancelacionDTO cancelar(Long id) {
        AnuncioEntity anuncio = anuncioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Anuncio no encontrado con id: " + id));

        if(anuncio.getEstado() == EstadoAnuncio.CANCELADO) {
            throw new AnuncioCanceladoException("El anuncio ya se encuentra cancelado");
        }

        double montoReembolsado = calcularReembolso(anuncio);
        //Al cancelar un anuncio, su estado debe cambiar a “Cancelado”.
        anuncio.setEstado(EstadoAnuncio.CANCELADO);
        anuncioRepository.save(anuncio);

        //cargamos el DTO de cancelacion
        CancelacionDTO response = new CancelacionDTO();
        response.setAnuncio(anuncioMapper.toDto(anuncio));
        response.setMontoReembolsado(montoReembolsado);
        return response;
    }

    // El sistema deberá permitir visualizar todos los anuncios de una campaña especifica.
    @Transactional(readOnly = true)
    public List<AnuncioResponseDTO> listarPorCampana(Long idCampana) {
        campanaRepository.findById(idCampana)
                .orElseThrow(() -> new RecursoNoEncontradoException("Campana no encontrada con ID: " + idCampana));

        return anuncioRepository.findByCampanaId(idCampana).stream()
                .map(anuncioMapper::toDto)
                .toList();
    }

    //Control de Presupuestos
    public void validarLimiteOverbudget(CampanaEntity campana, double nuevaInversion) {
        double totalPendiente = anuncioRepository.findByCampanaId(campana.getId()).stream()
                .filter(a -> a.getEstado() == EstadoAnuncio.PENDIENTE)
                .mapToDouble(AnuncioEntity::getInversionEstimada)
                .sum();

        double limiteOverBudget = campana.getPresupuestoBase() * OVERBUDGET_FACTOR;

        if(totalPendiente + nuevaInversion > limiteOverBudget) {
            throw new LimiteOverbudgetSuperadoException("El anuncio supera el limite de overbudget permitido" +
                    "Limite: " + limiteOverBudget +
                    ", Total en pendiente: " + totalPendiente +
                    ", Nueva inversion: " + nuevaInversion);
        }
    }

    //Redistribucion con otro anuncio
    private boolean intentarRedistribucion(AnuncioEntity anuncioActual) {
        return anuncioRepository.findByCampanaId(anuncioActual.getCampana().getId()).stream()
                .filter(a -> a.getEstado() == EstadoAnuncio.PENDIENTE)
                .filter(a -> !a.getId().equals(anuncioActual.getId()))
                .filter(a -> !a.getAnunciante().getEsCuentaVip())
                .findFirst()
                .map(candidato -> {
                    //si encontramos un candidato hacemos la transferencia
                    candidato.setEstado(EstadoAnuncio.CANCELADO);
                    anuncioActual.setInversionEstimada(anuncioActual.getInversionEstimada() + candidato.getInversionEstimada());
                    return true;
                })
                .orElse(false);
    }

    //Política de Reembolso Monetario
    private double calcularReembolso(AnuncioEntity anuncio) {
        double factor = anuncio.getAnunciante().getEsCuentaVip() ? REEMBOLSO_VIP : REEMBOLSO_REGULAR;
        return anuncio.getInversionEstimada() * factor;
    }
}
