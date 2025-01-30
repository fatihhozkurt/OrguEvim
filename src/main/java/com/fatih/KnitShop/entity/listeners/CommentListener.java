package com.fatih.KnitShop.entity.listeners;

import com.fatih.KnitShop.entity.CommentEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

import static com.fatih.KnitShop.consts.RecordStatus.ACTIVE;

@Component
public class CommentListener {
    @PrePersist
    public void prePersist(CommentEntity commentEntity) {
        commentEntity.setLikeCount(0L);
        commentEntity.setReplyCount(0L);
        commentEntity.setRecordStatus(ACTIVE);
    }

    @PreUpdate
    public void preDelete(CommentEntity commentEntity) {
        if (commentEntity.isRecordStatus()) {
            if (commentEntity.getSupComment() != null && commentEntity.getSupComment().getId() != null) {
                commentEntity.setReplyCount(0L);
            }
            commentEntity.setLikeCount(0L);
        }
    }
}
