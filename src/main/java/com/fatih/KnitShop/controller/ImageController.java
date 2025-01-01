package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.ImageControllerApi;
import com.fatih.KnitShop.dto.request.image.UploadImageRequest;
import com.fatih.KnitShop.dto.response.image.ImageResponse;
import com.fatih.KnitShop.entity.ImageEntity;
import com.fatih.KnitShop.mapper.ImageMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
public class ImageController implements ImageControllerApi {

    @Override
    public ResponseEntity<ImageResponse> uploadImage(List<UploadImageRequest> uploadImageRequest) {
        return null;
    }

    @Override
    public ResponseEntity<List<ImageResponse>> getAllImages() {
        return null;
    }

    @Override
    public ResponseEntity<ImageResponse> getImageById(UUID imageId) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> deleteImageById(UUID imageId) {
        return null;
    }
}
