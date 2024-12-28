package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.api.LikeControllerApi;
import com.fatih.KnitShop.dto.request.like.*;
import com.fatih.KnitShop.dto.response.like.LikeResponse;
import com.fatih.KnitShop.dto.response.like.UnlikeResponse;
import com.fatih.KnitShop.dto.response.user.UserMiniProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class LikeController implements LikeControllerApi {
    @Override
    public ResponseEntity<LikeResponse> likePost(LikePostRequest likePostRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UnlikeResponse> unlikePost(UnlikePostRequest unlikePostRequest) {
        return null;
    }

    @Override
    public ResponseEntity<LikeResponse> likeComment(LikeCommentRequest likeCommentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UnlikeResponse> unlikeComment(UnlikeCommentRequest unlikeCommentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<LikeResponse> likeReply(LikeReplyRequest likeReplyRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UnlikeResponse> unlikeReply(UnlikeReplyRequest unlikeReplyRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UserMiniProfile> getLikesByPostId(UUID ownerId, UUID postId) {
        return null;
    }

    @Override
    public ResponseEntity<UserMiniProfile> getLikesByCommentId(UUID ownerId, UUID postId, UUID commentId) {
        return null;
    }

    @Override
    public ResponseEntity<UserMiniProfile> getLikesByReplyId(UUID ownerId, UUID postId, UUID commentId, UUID replyId) {
        return null;
    }
}