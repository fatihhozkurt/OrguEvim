package com.fatih.KnitShop.controller.api;

import com.fatih.KnitShop.dto.request.comment.CreateCommentRequest;
import com.fatih.KnitShop.dto.request.comment.CreateReplyRequest;
import com.fatih.KnitShop.dto.request.comment.DeleteCommentRequest;
import com.fatih.KnitShop.dto.request.comment.DeleteReplyRequest;
import com.fatih.KnitShop.dto.response.comment.CommentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.fatih.KnitShop.url.UrlConst.*;

@RequestMapping(COMMENT)
public interface CommentControllerApi {

    @PostMapping(COMMENT)
    ResponseEntity<HttpStatus> createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest);

    @PostMapping(REPLY)
    ResponseEntity<HttpStatus> createReply(@Valid @RequestBody CreateReplyRequest createReplyRequest);

    @DeleteMapping(COMMENT)
    ResponseEntity<HttpStatus> deleteComment(@Valid @RequestBody DeleteCommentRequest deleteCommentRequest);

    @DeleteMapping(REPLY)
    ResponseEntity<HttpStatus> deleteReply(@Valid @RequestBody DeleteReplyRequest deleteReplyRequest);

    @GetMapping(COMMENT + ID)
    ResponseEntity<PageImpl<CommentResponse>> getCommentsByPostId(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                                  @RequestParam("postId") @NotNull UUID postId);

    @GetMapping(REPLY + ID)
    ResponseEntity<PageImpl<CommentResponse>> getRepliesByCommentId(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                                    @RequestParam("postId") @NotNull UUID postId,
                                                                    @RequestParam("commentId") @NotNull UUID commentId);
}