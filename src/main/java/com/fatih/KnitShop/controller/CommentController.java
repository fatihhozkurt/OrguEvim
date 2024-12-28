package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.api.CommentControllerApi;
import com.fatih.KnitShop.dto.request.comment.CreateCommentRequest;
import com.fatih.KnitShop.dto.request.comment.CreateReplyRequest;
import com.fatih.KnitShop.dto.request.comment.DeleteCommentRequest;
import com.fatih.KnitShop.dto.request.comment.DeleteReplyRequest;
import com.fatih.KnitShop.dto.response.comment.CommentResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.fatih.KnitShop.url.UrlConst.COMMENT;

@RestController
public class CommentController implements CommentControllerApi {
    @Override
    public ResponseEntity<HttpStatus> createComment(CreateCommentRequest createCommentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> createReply(CreateReplyRequest createReplyRequest) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> deleteComment(DeleteCommentRequest deleteCommentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> deleteReply(DeleteReplyRequest deleteReplyRequest) {
        return null;
    }

    @Override
    public ResponseEntity<PageImpl<CommentResponse>> getCommentsByPostId(UUID ownerId, UUID postId) {
        return null;
    }

    @Override
    public ResponseEntity<PageImpl<CommentResponse>> getRepliesByCommentId(UUID ownerId, UUID postId, UUID commentId) {
        return null;
    }
}
