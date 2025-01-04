package com.fatih.KnitShop.controller.api;

import com.fatih.KnitShop.dto.request.comment.CreateCommentRequest;
import com.fatih.KnitShop.dto.request.comment.CreateReplyRequest;
import com.fatih.KnitShop.dto.request.comment.DeleteCommentRequest;
import com.fatih.KnitShop.dto.request.comment.DeleteReplyRequest;
import com.fatih.KnitShop.dto.response.comment.CommentResponse;
import com.fatih.KnitShop.dto.response.comment.ReplyResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.fatih.KnitShop.url.UrlConst.*;

@RequestMapping(COMMENT)
public interface CommentControllerApi {

    //CheckedX
    @PostMapping
    ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest);

    //CheckedX
    @PostMapping(REPLY)
    ResponseEntity<ReplyResponse> createReply(@Valid @RequestBody CreateReplyRequest createReplyRequest);

    //CheckedX
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteComment(@Valid @RequestBody DeleteCommentRequest deleteCommentRequest);

    //CheckedX
    @DeleteMapping(REPLY)
    ResponseEntity<HttpStatus> deleteReply(@Valid @RequestBody DeleteReplyRequest deleteReplyRequest);

    //CheckedX
    @GetMapping(ID)
    ResponseEntity<PageImpl<CommentResponse>> getCommentsByPostId(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                                  @RequestParam("postId") @NotNull UUID postId,
                                                                  Pageable pageable);

    //Checked
    @GetMapping(REPLY + ID)
    ResponseEntity<PageImpl<ReplyResponse>> getRepliesByCommentId(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                                    @RequestParam("postId") @NotNull UUID postId,
                                                                    @RequestParam("commentId") @NotNull UUID commentId,
                                                                    Pageable pageable);
}