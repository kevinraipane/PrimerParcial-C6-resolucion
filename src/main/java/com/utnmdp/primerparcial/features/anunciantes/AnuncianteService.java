package com.utnmdp.primerparcial.features.anunciantes;

import com.utnmdp.primerparcial.common.exceptions.RecursoNoEncontradoException;
import com.utnmdp.primerparcial.features.anunciantes.dtos.AnuncianteRequestDTO;
import com.utnmdp.primerparcial.features.anunciantes.dtos.AnuncianteResponseDTO;
import com.utnmdp.primerparcial.features.anunciantes.excepciones.AnunciosActivosException;
import com.utnmdp.primerparcial.features.anunciantes.excepciones.CuitDuplicadoException;
import com.utnmdp.primerparcial.features.anunciantes.mapper.AnuncianteMapper;
import com.utnmdp.primerparcial.features.anuncios.AnuncioRepository;
import com.utnmdp.primerparcial.features.anuncios.EstadoAnuncio;
import com.utnmdp.primerparcial.features.campanas.CampanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnuncianteService {
    private final AnuncianteRepository anuncianteRepository;
    private final AnuncioRepository anuncioRepository;
    private final CampanaRepository campanaRepository;
    private AnuncianteMapper anuncianteMapper;

    //El sistema deberá permitir que se realicen operaciones de ALTA y BAJA sobre
    //anunciantes.

    //ALTA
    @Transactional
    public AnuncianteResponseDTO crear(AnuncianteRequestDTO dto) {
        if (anuncianteRepository.existsByCuit(dto.getCuit())) {
            throw new CuitDuplicadoException("Ya existe un anunciante con el CUIT: " + dto.getCuit()); // a nivel seguridad es horrible esto jaja
        }

        AnuncianteEntity entity = anuncianteMapper.toEntity(dto);
        entity.setActivo(true);
        return anuncianteMapper.toDto(anuncianteRepository.save(entity));

    }

    //BAJA
    //La baja deberá ser lógica. No deberá poder darse de baja un anunciante si
    //tienen algún anuncio pendiente o en curso
    @Transactional
    public AnuncianteResponseDTO darDeBaja(Long id) {
        AnuncianteEntity anunciante = anuncianteRepository.findById(id)
                .orElseThrow( () -> new RecursoNoEncontradoException("Anunciante no encontrado con ID: " +id));

        boolean tieneAnunciosActivos = anuncioRepository.findByAnuncianteId(id).stream()
                .anyMatch(a -> a.getEstado() == EstadoAnuncio.PENDIENTE || a.getEstado() == EstadoAnuncio.EN_CURSO);

        if(tieneAnunciosActivos) {
            throw new AnunciosActivosException("No se puede dar de baja al anunciante porque tieen anuncios en pendiente o en curso");
        }

        anunciante.setActivo(false);
        return anuncianteMapper.toDto(anuncianteRepository.save(anunciante));
    }

    //El sistema deberá permitir que se visualicen todos los anunciantes.
    //○ Deberá permitir filtrados opcionales por nombre de empresa y/o cuit
    @Transactional(readOnly = true)
    public List<AnuncianteResponseDTO> listar(String nombre, String cuit) {
        return anuncianteRepository.findAll().stream()
                .filter(a -> nombre == null || a.getNombreEmpresa().toLowerCase().contains(nombre.toLowerCase()))
                .filter(a -> cuit == null || a.getCuit().equals(cuit))
                .map(anuncianteMapper::toDto)
                .toList();
    }

    //El sistema deberá permitir visualizar todos los anunciantes pertenecientes a una
    //campaña en particular.
    @Transactional(readOnly = true)
    public List<AnuncianteResponseDTO> listarPorCampana(Long idCampana) {
        campanaRepository.findById(idCampana)
                .orElseThrow(() -> new RecursoNoEncontradoException("Campaña no encontrada con ID: " +idCampana));

        return anuncioRepository.findByCampanaId(idCampana).stream()
                .map(anuncio -> anuncio.getAnunciante())
                .distinct()
                .map(anuncianteMapper::toDto)
                .toList();
    }
}
