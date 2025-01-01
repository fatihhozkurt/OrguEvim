package com.fatih.KnitShop.entity.listeners;

import com.fatih.KnitShop.entity.PostEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Component
public class PostListener {

    @PrePersist
    public void prePersist(PostEntity post) {
        post.setCommentCount(0L);
        post.setLikeCount(0L);
    }

    @PreUpdate
    public void preUpdate(PostEntity post) {
        if (post.isRecordStatus()) {
            post.getComments().forEach(comment -> comment.setRecordStatus(PASSIVE));
            post.getLikes().forEach(like -> like.setRecordStatus(PASSIVE));
            post.getImages().forEach(image -> image.setRecordStatus(PASSIVE));
            post.getCoverImage().setRecordStatus(PASSIVE);
            post.setImageCount(0L);
            post.setCommentCount(0L);
            post.setLikeCount(0L);
        }
    }
}