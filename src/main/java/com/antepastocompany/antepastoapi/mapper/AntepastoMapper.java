package com.antepastocompany.antepastoapi.mapper;

import com.antepastocompany.antepastoapi.dto.request.AntepastoDTO;
import com.antepastocompany.antepastoapi.entity.Antepasto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AntepastoMapper {

    AntepastoMapper INSTANCE = Mappers.getMapper(AntepastoMapper.class);

    @Mapping(source = "spicy", target = "spicy")
    Antepasto toModel(AntepastoDTO antepastoDTO);

    @Mapping(source = "spicy", target = "spicy")
    AntepastoDTO toDTO(Antepasto antepasto);

}
