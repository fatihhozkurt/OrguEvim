package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.entity.ImageEntity;
import com.fatih.KnitShop.exception.ResourceNotFoundException;
import com.fatih.KnitShop.manager.service.ImageService;
import com.fatih.KnitShop.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.fatih.KnitShop.consts.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class ImageManager implements ImageService {

    private final ImageRepository imageRepository;
    private final MessageSource messageSource;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<ImageEntity> getAllImages() {
        return imageRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ImageEntity getImageById(UUID imageId) {

        return imageRepository.findById(imageId).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.IMG001",
                        new Object[]{imageId},
                        Locale.getDefault())));
    }

    @Transactional
    @Override
    public List<ImageEntity> uploadImage(List<ImageEntity> imageEntities) {
        return imageRepository.saveAll(imageEntities);
    }

    @Transactional
    @Override
    public void deleteImage(UUID imageId) {
        ImageEntity foundImage = getImageById(imageId);
        foundImage.setRecordStatus(PASSIVE);
        imageRepository.save(foundImage);
    }
}