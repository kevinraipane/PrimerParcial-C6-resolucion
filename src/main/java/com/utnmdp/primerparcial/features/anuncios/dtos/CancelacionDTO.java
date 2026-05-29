package com.utnmdp.primerparcial.features.anuncios.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelacionDTO {
    private AnuncioResponseDTO anuncio; //si se puede devolver un DTO dentro de otro DTO
    private Double montoReembolsado;
}

// Sedeberá retornar un CancelacionDTO, que contenga la información del
//anuncio cancelado, asi como el monto que fue reembolsado.
