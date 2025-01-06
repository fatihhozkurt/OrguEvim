package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.LikeControllerApi;
import com.fatih.KnitShop.dto.request.like.*;
import com.fatih.KnitShop.dto.response.like.*;
import com.fatih.KnitShop.dto.response.user.UserMiniProfileResponse;
import com.fatih.KnitShop.entity.LikeEntity;
import com.fatih.KnitShop.entity.UserEntity;
import com.fatih.KnitShop.manager.service.LikeService;
import com.fatih.KnitShop.mapper.LikeMapper;
import com.fatih.KnitShop.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class LikeController implements LikeControllerApi {

    private final LikeService likeService;

    @Override
    public ResponseEntity<LikePostResponse> likePost(LikePostRequest likePostRequest) {

        LikeEntity likeEntity = likeService.likePost(likePostRequest.ownerId(),
                likePostRequest.postId(),
                likePostRequest.requesterId());

        LikePostResponse likePostResponse = LikeMapper.INSTANCE.toLikePostResponse(likeEntity);
        return new ResponseEntity<>(likePostResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UnlikePostResponse> unlikePost(UnlikePostRequest unlikePostRequest) {

        LikeEntity deletedLike = likeService.unlikePost(unlikePostRequest.ownerId(),
                unlikePostRequest.postId(),
                unlikePostRequest.likeId(),
                unlikePostRequest.requesterId());

        UnlikePostResponse unlikePostResponse = LikeMapper.INSTANCE.toUnlikePostResponse(deletedLike);

        return new ResponseEntity<>(unlikePostResponse, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<LikeCommentResponse> likeComment(LikeCommentRequest likeCommentRequest) {

        LikeEntity savedLike = likeService.likeComment(likeCommentRequest.ownerId(),
                likeCommentRequest.postId(),
                likeCommentRequest.commentId(),
                likeCommentRequest.requesterId());

        LikeCommentResponse likeCommentResponse = LikeMapper.INSTANCE.toLikeCommentResponse(savedLike);

        return new ResponseEntity<>(likeCommentResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UnlikeCommentResponse> unlikeComment(UnlikeCommentRequest unlikeCommentRequest) {

        LikeEntity deletedLike = likeService.unlikeComment(unlikeCommentRequest.ownerId(),
                unlikeCommentRequest.postId(),
                unlikeCommentRequest.commentId(),
                unlikeCommentRequest.likeId(),
                unlikeCommentRequest.requesterId());

        UnlikeCommentResponse unlikeCommentResponse = LikeMapper.INSTANCE.toUnlikeCommentResponse(deletedLike);

        return new ResponseEntity<>(unlikeCommentResponse, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<LikeReplyResponse> likeReply(LikeReplyRequest likeReplyRequest) {

        LikeEntity savedLike = likeService.likeReply(
                likeReplyRequest.ownerId(),
                likeReplyRequest.postId(),
                likeReplyRequest.commentId(),
                likeReplyRequest.replyId(),
                likeReplyRequest.requesterId());

        LikeReplyResponse likeReplyResponse = LikeMapper.INSTANCE.toLikeReplyResponse(savedLike);

        return new ResponseEntity<>(likeReplyResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UnlikeReplyResponse> unlikeReply(UnlikeReplyRequest unlikeReplyRequest) {

        LikeEntity removedLike = likeService.unlikeReply(
                unlikeReplyRequest.ownerId(),
                unlikeReplyRequest.postId(),
                unlikeReplyRequest.commentId(),
                unlikeReplyRequest.replyId(),
                unlikeReplyRequest.likeId(),
                unlikeReplyRequest.requesterId());

        UnlikeReplyResponse unlikeReplyResponse = LikeMapper.INSTANCE.toUnlikeReplyResponse(removedLike);

        return new ResponseEntity<>(unlikeReplyResponse, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<PageImpl<UserMiniProfileResponse>> getLikesByPostId(UUID ownerId, UUID postId, Pageable pageable) {

        Page<UserEntity> foundUsers = likeService.getLikesByPostId(ownerId, postId, pageable);
        List<UserMiniProfileResponse> userMiniProfileResponses =
                UserMapper.INSTANCE.toUserMiniProfileResponseList(foundUsers.getContent());

        return new ResponseEntity<>(new PageImpl<>(userMiniProfileResponses,
                pageable,
                foundUsers.getTotalElements()),
                HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<PageImpl<UserMiniProfileResponse>> getLikesByCommentId(UUID ownerId, UUID postId, UUID commentId, Pageable pageable) {
        Page<UserEntity> foundUsers = likeService.getLikesByCommentId(ownerId, postId, commentId, pageable);
        List<UserMiniProfileResponse> userMiniProfileResponses =
                UserMapper.INSTANCE.toUserMiniProfileResponseList(foundUsers.getContent());

        return new ResponseEntity<>(new PageImpl<>(userMiniProfileResponses,
                pageable,
                foundUsers.getTotalElements()), HttpStatus.FOUND);
    }
}