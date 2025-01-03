package com.fatih.KnitShop.manager.service;

import com.fatih.KnitShop.entity.CommentEntity;

import java.util.UUID;

public interface CommentService {


    void checkComment(UUID commentId);

    CommentEntity getCommentById(UUID ownerId, UUID postId, UUID commentId);
}
