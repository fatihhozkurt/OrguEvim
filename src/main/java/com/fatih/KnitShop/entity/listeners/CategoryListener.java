package com.fatih.KnitShop.entity.listeners;

import com.fatih.KnitShop.entity.CategoryEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

import static com.fatih.KnitShop.url.RecordStatus.ACTIVE;

@Component
public class CategoryListener {

    //Checked
    @PrePersist
    public void prePersist(CategoryEntity category) {

        category.setPostCount(0L);
        category.setRecordStatus(ACTIVE);
    }

    //Checked
    @PreUpdate
    public void preDelete(CategoryEntity category) {
        if (category.isRecordStatus()) {
            category.setPostCount(0L);
        }
    }
}
