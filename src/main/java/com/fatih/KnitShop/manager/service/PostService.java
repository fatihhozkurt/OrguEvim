package com.fatih.KnitShop.manager.service;

import com.fatih.KnitShop.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PostService {
    Page<PostEntity> getAllPosts(Pageable pageable);

    Page<PostEntity> getRandomPosts(Pageable pageable);

    PostEntity getPostById(UUID ownerId, UUID postId);

    Page<PostEntity> getPostsByUserId(UUID ownerId, Pageable pageable);

    Page<PostEntity> getPostsByCategoryId(UUID categoryId, Pageable pageable);

    PostEntity createPost(PostEntity postEntity, UUID requesterId);

    void deletePost(UUID ownerId, UUID postId, UUID requesterId);

    PostEntity updatePost(PostEntity postEntity, UUID requesterId);

    void deleteAllPosts();
}
