package com.fatih.KnitShop.manager.service;

import com.fatih.KnitShop.entity.CommentEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CommentService {


    void checkComment(UUID commentId);

    CommentEntity getCommentById(UUID ownerId, UUID postId, UUID commentId);

    CommentEntity getReplyById(UUID ownerId, UUID postId, UUID commentId, UUID replyId);

    Page<CommentEntity> getCommentsByPostId(UUID ownerId, UUID postId, Pageable pageable);

    Page<CommentEntity> getRepliesByCommentId(UUID ownerId, UUID postId, UUID commentId, Pageable pageable);

    CommentEntity createComment(UUID ownerId, UUID postId, UUID requesterId, String content);

    CommentEntity createReply(UUID ownerId, UUID postId, UUID commentId, UUID requesterId, String content);

    void deleteComment(UUID ownerId, UUID postId, UUID commentId, UUID requesterId);

    void deleteReply(UUID ownerId, UUID postId, UUID commentId, UUID replyId, UUID requesterId);

    void softDeleteComment(CommentEntity foundComment);

    void softDeleteReply(CommentEntity foundReply);
}
