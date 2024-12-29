package com.fatih.KnitShop.manager.service;

import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.dto.request.user.UserUnfollowRequest;
import com.fatih.KnitShop.entity.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity getUserById(UUID userId);

    void follow(UserFollowRequest userFollowRequest);

    void unfollow(UserUnfollowRequest userUnfollowRequest);
}
