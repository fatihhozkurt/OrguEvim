package com.fatih.KnitShop.api;

import com.fatih.KnitShop.dto.request.like.*;
import com.fatih.KnitShop.dto.response.like.LikeResponse;
import com.fatih.KnitShop.dto.response.like.UnlikeResponse;
import com.fatih.KnitShop.dto.response.user.UserMiniProfile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.fatih.KnitShop.url.UrlConst.*;

@RequestMapping(LIKE)
public interface LikeControllerApi {

    @PostMapping(POST)
    ResponseEntity<LikeResponse> likePost(@Valid @RequestBody LikePostRequest likePostRequest);

    @DeleteMapping(POST)
    ResponseEntity<UnlikeResponse> unlikePost(@Valid @RequestBody UnlikePostRequest unlikePostRequest);

    @PostMapping(COMMENT)
    ResponseEntity<LikeResponse> likeComment(@Valid @RequestBody LikeCommentRequest likeCommentRequest);

    @DeleteMapping(COMMENT)
    ResponseEntity<UnlikeResponse> unlikeComment(@Valid @RequestBody UnlikeCommentRequest unlikeCommentRequest);

    @PostMapping(REPLY)
    ResponseEntity<LikeResponse> likeReply(@Valid @RequestBody LikeReplyRequest likeReplyRequest);

    @DeleteMapping(REPLY)
    ResponseEntity<UnlikeResponse> unlikeReply(@Valid @RequestBody UnlikeReplyRequest unlikeReplyRequest);

    @GetMapping(POST + ID)
    ResponseEntity<UserMiniProfile> getLikesByPostId(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                     @RequestParam("postId") @NotNull UUID postId);

    @GetMapping(COMMENT + ID)
    ResponseEntity<UserMiniProfile> getLikesByCommentId(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                        @RequestParam("postId") @NotNull UUID postId,
                                                        @RequestParam("commentId") @NotNull UUID commentId);

    @GetMapping(REPLY + ID)
    ResponseEntity<UserMiniProfile> getLikesByReplyId(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                      @RequestParam("postId") @NotNull UUID postId,
                                                      @RequestParam("commentId") @NotNull UUID commentId,
                                                      @RequestParam("replyId") @NotNull UUID replyId);
}