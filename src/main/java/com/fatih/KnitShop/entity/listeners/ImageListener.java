package com.fatih.KnitShop.entity.listeners;

import com.fatih.KnitShop.entity.ImageEntity;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

import static com.fatih.KnitShop.url.RecordStatus.ACTIVE;

@Component
public class ImageListener {

    @PrePersist
    public void prePersist(ImageEntity image) {
        image.setRecordStatus(ACTIVE);
    }
}