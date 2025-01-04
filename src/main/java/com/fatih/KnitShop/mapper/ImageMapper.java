package com.fatih.KnitShop.mapper;

import com.fatih.KnitShop.dto.request.image.UpdateImageRequest;
import com.fatih.KnitShop.dto.request.image.UploadImageRequest;
import com.fatih.KnitShop.dto.response.image.ImageResponse;
import com.fatih.KnitShop.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    //Checked
    @Mapping(target = "imageId", source = "id")
    ImageResponse toImageResponse(ImageEntity imageEntity);

    //Checked
    List<ImageResponse> toImageResponseList(List<ImageEntity> imageEntities);

    //Checked
    ImageEntity UploadImageRequestToEntity(UploadImageRequest uploadImageRequest);

    //Checked
    List<ImageEntity> UploadImageRequestToEntityList(List<UploadImageRequest> uploadImageRequests);

    //Checked
    ImageEntity UpdateImageRequestToEntity(UpdateImageRequest updateImageRequest);

    List<ImageEntity> UpdateImageRequestToEntityList(List<UpdateImageRequest> updateImageRequests);
}
