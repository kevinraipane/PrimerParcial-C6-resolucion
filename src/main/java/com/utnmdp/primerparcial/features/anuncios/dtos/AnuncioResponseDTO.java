package com.utnmdp.primerparcial.features.anuncios.dtos;

import com.utnmdp.primerparcial.features.anuncios.EstadoAnuncio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnuncioResponseDTO {
    private Long id;
    private String codigoAnuncio;
    private LocalDateTime fechaCreacion;
    private Double inversionEstimada;
    private Double inversionTotal;
    private EstadoAnuncio estado;
    private Long idCampana;
    private Long idAnunciante;
}
