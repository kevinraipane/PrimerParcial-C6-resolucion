package com.utnmdp.primerparcial.features.anuncios.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastoRequestDTO {
    @NotNull(message = "El monto del gasto es obligatorio")
    @Min(value = 1, message = "El monto debe ser mayor a cero")
    private Double monto;
}
