package com.fatih.KnitShop.entity.listeners;

import com.fatih.KnitShop.entity.PostEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

import static com.fatih.KnitShop.consts.RecordStatus.ACTIVE;

@Component
public class PostListener {

    @PrePersist
    public void prePersist(PostEntity post) {
        post.setRecordStatus(ACTIVE);
        post.setCommentCount(0L);
        post.setLikeCount(0L);
    }

    @PreUpdate
    public void preUpdate(PostEntity post) {
        if (post.isRecordStatus()) {
            post.setImageCount(0L);
            post.setCommentCount(0L);
            post.setLikeCount(0L);
        }
    }
}