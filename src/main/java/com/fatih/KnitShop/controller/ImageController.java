package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.ImageControllerApi;
import com.fatih.KnitShop.dto.request.image.UploadImageRequest;
import com.fatih.KnitShop.dto.response.image.ImageResponse;
import com.fatih.KnitShop.entity.ImageEntity;
import com.fatih.KnitShop.manager.service.ImageService;
import com.fatih.KnitShop.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class ImageController implements ImageControllerApi {

    private final ImageService imageService;

    @Override
    public ResponseEntity<List<ImageResponse>> uploadImage(List<UploadImageRequest> uploadImageRequest) {

        List<ImageEntity> imageEntities = ImageMapper.INSTANCE.UploadImageRequestToEntityList(uploadImageRequest);
        List<ImageEntity> savedImages = imageService.uploadImage(imageEntities);
        List<ImageResponse> imageResponses = ImageMapper.INSTANCE.toImageResponseList(savedImages);

        return new ResponseEntity<>(imageResponses, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<ImageResponse>> getAllImages() {

        List<ImageEntity> foundImages = imageService.getAllImages();
        List<ImageResponse> imageResponses = ImageMapper.INSTANCE.toImageResponseList(foundImages);

        return new ResponseEntity<>(imageResponses, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ImageResponse> getImageById(UUID imageId) {

        ImageEntity foundImage = imageService.getImageById(imageId);
        ImageResponse imageResponse = ImageMapper.INSTANCE.toImageResponse(foundImage);

        return new ResponseEntity<>(imageResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteImageById(UUID imageId) {

        imageService.deleteImage();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
