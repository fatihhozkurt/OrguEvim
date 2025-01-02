package com.fatih.KnitShop.manager.service;

import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserEntity getUserById(UUID userId);

    void follow(UserFollowRequest userFollowRequest);

    void unfollow(UUID unfollowerId, UUID unfollowingId, UUID requesterId);

    List<UserEntity> getAllUsers();

    UserEntity createUser(UserEntity user);

    UserEntity updateUser(UserEntity user, UUID userId);

    void deleteUser(UUID ownerId, UUID requesterId);

    Page<UserEntity> getFollowersById(UUID ownerId, Pageable pageable);

    Page<UserEntity> getFollowingsById(UUID ownerId, Pageable pageable);

    void checkUser(UUID userId);
}