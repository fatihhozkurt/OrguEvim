package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.CommentControllerApi;
import com.fatih.KnitShop.dto.request.comment.CreateCommentRequest;
import com.fatih.KnitShop.dto.request.comment.CreateReplyRequest;
import com.fatih.KnitShop.dto.request.comment.DeleteCommentRequest;
import com.fatih.KnitShop.dto.request.comment.DeleteReplyRequest;
import com.fatih.KnitShop.dto.response.comment.CommentResponse;
import com.fatih.KnitShop.dto.response.comment.ReplyResponse;
import com.fatih.KnitShop.entity.CommentEntity;
import com.fatih.KnitShop.manager.service.CommentService;
import com.fatih.KnitShop.mapper.CommentMapper;
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
public class CommentController implements CommentControllerApi {

    private final CommentService commentService;

    @Override
    public ResponseEntity<CommentResponse> createComment(CreateCommentRequest createCommentRequest) {

        CommentEntity savedComment = commentService.createComment(
                createCommentRequest.ownerId(),
                createCommentRequest.postId(),
                createCommentRequest.requesterId(),
                createCommentRequest.content());

        CommentResponse commentResponse = CommentMapper.INSTANCE.toCommentResponse(savedComment);

        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ReplyResponse> createReply(CreateReplyRequest createReplyRequest) {

        CommentEntity savedReply = commentService.createReply(
                createReplyRequest.ownerId(),
                createReplyRequest.postId(),
                createReplyRequest.commentId(),
                createReplyRequest.requesterId(),
                createReplyRequest.content());

        ReplyResponse replyResponse = CommentMapper.INSTANCE.toReplyResponse(savedReply);

        return new ResponseEntity<>(replyResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteComment(DeleteCommentRequest deleteCommentRequest) {

        commentService.deleteComment(
                deleteCommentRequest.ownerId(),
                deleteCommentRequest.postId(),
                deleteCommentRequest.commentId(),
                deleteCommentRequest.requesterId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteReply(DeleteReplyRequest deleteReplyRequest) {

        commentService.deleteReply(
                deleteReplyRequest.ownerId(),
                deleteReplyRequest.postId(),
                deleteReplyRequest.commentId(),
                deleteReplyRequest.replyId(),
                deleteReplyRequest.requesterId()
        );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<PageImpl<CommentResponse>> getCommentsByPostId(UUID ownerId, UUID postId, Pageable pageable) {

        Page<CommentEntity> foundComments = commentService.getCommentsByPostId(ownerId, postId, pageable);
        List<CommentResponse> commentResponses = CommentMapper.INSTANCE.toCommentResponseList(foundComments.getContent());

        return new ResponseEntity<>(new PageImpl<>(commentResponses,
                pageable,
                foundComments.getTotalElements()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageImpl<ReplyResponse>> getRepliesByCommentId(UUID ownerId, UUID postId, UUID commentId, Pageable pageable) {

        Page<CommentEntity> foundComments = commentService.getRepliesByCommentId(ownerId, postId, commentId, pageable);
        List<ReplyResponse> commentResponses = CommentMapper.INSTANCE.toReplyResponseList(foundComments.getContent());

        return new ResponseEntity<>(new PageImpl<>(
                commentResponses,
                pageable,
                foundComments.getTotalElements()),
                HttpStatus.FOUND);
    }
}
