package com.fatih.KnitShop.manager.service;

import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.dto.request.user.UserUnfollowRequest;
import com.fatih.KnitShop.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserEntity getUserById(UUID userId);

    void follow(UserFollowRequest userFollowRequest);

    void unfollow(UserUnfollowRequest userUnfollowRequest);

    List<UserEntity> getAllUsers();

    UserEntity createUser(UserEntity user);

    UserEntity updateUser(UserEntity user);

    void deleteUser(UUID userId);

    Page<UserEntity> getFollowersById(UUID userId, Pageable pageable);

    Page<UserEntity> getFollowingsById(UUID userId, Pageable pageable);
}
