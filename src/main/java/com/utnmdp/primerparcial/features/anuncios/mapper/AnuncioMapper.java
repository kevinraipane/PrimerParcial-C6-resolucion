package com.utnmdp.primerparcial.features.anuncios.mapper;

import com.utnmdp.primerparcial.features.anuncios.AnuncioEntity;
import com.utnmdp.primerparcial.features.anuncios.dtos.AnuncioRequestDTO;
import com.utnmdp.primerparcial.features.anuncios.dtos.AnuncioResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnuncioMapper {
    @Mapping(target = "anunciante.id", source = "idAnunciante")
    @Mapping(target = "campana.id", source = "idCampana")
    AnuncioEntity toEntity(AnuncioRequestDTO dto);

    @Mapping(target = "idAnunciante", source = "anunciante.id")
    @Mapping(target = "idCampana", source = "campana.id")
    AnuncioResponseDTO toDto(AnuncioEntity entity);

//    public static AnuncioEntity toEntity(AnuncioRequestDTO dto) {
//        return AnuncioEntity.builder()
//                .inversionEstimada(dto.getInversionEstimada())
//                .anunciante(AnuncianteEntity.builder().id(dto.getIdAnunciante()).build())
//                .campana(CampanaEntity.builder().id(dto.getIdCampana()).build())
//                .build();
//                // El resto de campos NO los mapeamos aca, sino que los asignamos en el service
//    }
//
//    public static AnuncionResponseDTO toDto(AnuncioEntity entity) {
//        return AnuncionResponseDTO.builder()
//                .id(entity.getId())
//                .codigoAnuncio(entity.getCodigoAnuncio().toString())
//                .fechaCreacion(entity.getFechaCreacion())
//                .inversionEstimada(entity.getInversionEstimada())
//                .inversionTotal(entity.getInversionTotal())
//                .estado(entity.getEstado())
//                .idCampana(entity.getCampana().getId())
//                .idAnunciante(entity.getAnunciante().getId())
//                .build();
//    }
}
