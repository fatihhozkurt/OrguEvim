package com.fatih.KnitShop.mapper;

import com.fatih.KnitShop.dto.response.image.ImageResponse;
import com.fatih.KnitShop.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    ImageResponse toImageResponse(ImageEntity imageEntity);

}
