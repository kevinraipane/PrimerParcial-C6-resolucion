package com.utnmdp.primerparcial.features.anunciantes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnuncianteRequestDTO {
    @NotBlank(message = "El nombre de la empresa es obligatorio!!")
    private String nombreEmpresa;

    @NotBlank(message = "El CUIT es obligatorio!!")
    private String cuit;

    @NotNull(message = "Se debe indicar si la cuenta es VIP o es plebeyo")
    private Boolean esCuentaVip;
}
