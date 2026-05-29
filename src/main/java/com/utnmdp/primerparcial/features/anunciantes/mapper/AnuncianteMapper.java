package com.utnmdp.primerparcial.features.anunciantes.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.utnmdp.primerparcial.features.anunciantes.AnuncianteEntity;
import com.utnmdp.primerparcial.features.anunciantes.dtos.AnuncianteRequestDTO;
import com.utnmdp.primerparcial.features.anunciantes.dtos.AnuncianteResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnuncianteMapper {
    @Mapping(target = "id", ignore = true) //Ignoramos el id pq es autogenerado
    @Mapping(target = "activo", ignore = true) //Ignoramos el activo pq lo vamos a manejar en el service
    AnuncianteEntity toEntity(AnuncianteRequestDTO dto);

    //Como todos los nombres coinciden, no hace falta agregar @Mapping
    AnuncianteResponseDTO toDto(AnuncianteEntity entity);

//    public static AnuncianteEntity toEntity(AnuncianteRequestDTO dto) {
//        return AnuncianteEntity.builder()
//                .nombreEmpresa(dto.getNombreEmpresa())
//                .cuit(dto.getCuit())
//                .esCuentaVip(dto.getEsCuentaVip())
//                .build();
//    }
//
//    public static AnuncianteResponseDTO toDTO(AnuncianteEntity entity) {
//        return new AnuncianteResponseDTO(
//                entity.getId(),
//                entity.getNombreEmpresa(),
//                entity.getCuit(),
//                entity.getEsCuentaVip(),
//                entity.getActivo()
//        );
//    }

}

