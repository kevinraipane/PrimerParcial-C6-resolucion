package com.utnmdp.primerparcial.features.anuncios.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnuncioRequestDTO {

    @NotNull(message = "El ID del anunciante es obligatorio!")
    private Long idAnunciante;

    @NotNull(message = "El ID de la campana es obligatorio")
    private Long idCampana;

    @NotNull(message = "La inversion estimada es obligatoria")
    @Min(value = 1, message = "La inversion estimada debe ser mayor a cero")
    private Double inversionEstimada;
}
