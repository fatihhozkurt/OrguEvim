package com.fatih.KnitShop.entity.listeners;

import com.fatih.KnitShop.entity.UserEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.fatih.KnitShop.url.RecordStatus.*;

@Component
@RequiredArgsConstructor
public class UserListener {

    //Checked
    @PrePersist
    public void prePersist(UserEntity user) {
        user.setRecordStatus(ACTIVE);
        user.setFollowersCount(0L);
        user.setFollowingCount(0L);
        user.setPostCount(0L);
    }

    //Checked
    @PreUpdate
    public void preDelete(UserEntity user) {
        if (user.isRecordStatus()) {
            user.getFollowers().clear();
            user.getFollowing().clear();

            user.setFollowersCount(0L);
            user.setFollowingCount(0L);

            user.setPostCount(0L);
        }
    }
}