package com.fatih.KnitShop.entity.listeners;

import com.fatih.KnitShop.entity.UserEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

import static com.fatih.KnitShop.url.RecordStatus.*;

@Component
public class UserListener {

    @PrePersist
    public void prePersist(UserEntity user) {
        user.setRecordStatus(ACTIVE);
        user.setFollowersCount(0L);
        user.setFollowingCount(0L);
        user.setPostCount(0L);
    }

    @PreUpdate
    public void preDelete(UserEntity user) {

        if (user.isRecordStatus()) {

            user.getFollowers().forEach(follower -> {
                follower.setRecordStatus(PASSIVE);
            });

            user.getFollowing().forEach(following -> {
                following.setRecordStatus(PASSIVE);
            });

            user.getPosts().forEach(post -> {
                post.setRecordStatus(PASSIVE);
            });

            user.getComments().forEach(comment -> {
                comment.setRecordStatus(PASSIVE);
            });

            user.getAvatarImage().setRecordStatus(PASSIVE);
        }
    }

}