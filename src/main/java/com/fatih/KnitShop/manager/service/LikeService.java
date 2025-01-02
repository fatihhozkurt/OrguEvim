package com.fatih.KnitShop.manager.service;

import com.fatih.KnitShop.entity.LikeEntity;
import com.fatih.KnitShop.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LikeService {

    Page<UserEntity> getLikesByPostId(UUID ownerId, UUID postId, Pageable pageable);

    Page<UserEntity> getLikesByCommentId(UUID ownerId, UUID postId, UUID commentId, Pageable pageable);

    LikeEntity likePost(UUID ownerId, UUID postId, UUID requesterId);

    LikeEntity unlikePost(UUID ownerId, UUID postId, UUID likeId, UUID requesterId);

    LikeEntity likeComment(UUID ownerId, UUID postId, UUID commentId, UUID requesterId);

    LikeEntity unlikeComment(UUID ownerId, UUID postId, UUID commentId, UUID likeId, UUID requesterId);
}
