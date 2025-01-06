package com.fatih.KnitShop.manager.service;

import com.fatih.KnitShop.entity.ImageEntity;

import java.util.List;
import java.util.UUID;

public interface ImageService {
    List<ImageEntity> getAllImages();

    ImageEntity getImageById(UUID imageId);

    List<ImageEntity> uploadImage(List<ImageEntity> imageEntities);

    void deleteImage(UUID imageId);
}
