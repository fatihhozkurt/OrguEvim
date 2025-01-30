package com.fatih.KnitShop.entity.listeners;

import com.fatih.KnitShop.entity.LikeEntity;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

import static com.fatih.KnitShop.consts.RecordStatus.ACTIVE;

@Component
public class LikeListener {

    @PrePersist
    public void prePersist(LikeEntity likeEntity) {
        likeEntity.setRecordStatus(ACTIVE);
    }
}
