package com.fatih.KnitShop.mapper;

import com.fatih.KnitShop.dto.request.image.UploadImageRequest;
import com.fatih.KnitShop.dto.response.image.ImageResponse;
import com.fatih.KnitShop.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    ImageResponse toImageResponse(ImageEntity imageEntity);
    List<ImageResponse> toImageResponseList(List<ImageEntity> imageEntities);

    ImageEntity UploadImageRequesttoImageEntity(UploadImageRequest uploadImageRequest);
    List<ImageEntity> UploadImageRequesttoImageEntityList(List<UploadImageRequest> uploadImageRequestList);
}
