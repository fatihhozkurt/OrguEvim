package com.fatih.KnitShop.entity.listeners;

import com.fatih.KnitShop.entity.CategoryEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

import static com.fatih.KnitShop.url.RecordStatus.ACTIVE;
import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Component
public class CategoryListener {

    @PrePersist
    public void prePersist(CategoryEntity category) {

        category.setPostCount(0L);
        category.setRecordStatus(ACTIVE);
    }

    @PreUpdate
    public void preDelete(CategoryEntity category) {
        if (category.isRecordStatus()) {
            category.getPosts().forEach(post -> post.setRecordStatus(PASSIVE));
        }
    }
}
