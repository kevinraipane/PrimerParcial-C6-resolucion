package com.utnmdp.primerparcial.features.anunciantes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnuncianteResponseDTO {
    private Long id;
    private String nombreEmpresa;
    private String cuit;
    private Boolean esCuentaVip;
    private Boolean activo;
}
