package com.fatih.KnitShop.controller.api;

import com.fatih.KnitShop.dto.request.like.*;
import com.fatih.KnitShop.dto.response.like.LikeCommentResponse;
import com.fatih.KnitShop.dto.response.like.LikePostResponse;
import com.fatih.KnitShop.dto.response.like.UnlikeCommentResponse;
import com.fatih.KnitShop.dto.response.like.UnlikePostResponse;
import com.fatih.KnitShop.dto.response.user.UserMiniProfileResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.fatih.KnitShop.url.UrlConst.*;

@RequestMapping(LIKE)
public interface LikeControllerApi {

    //Checked
    @PostMapping(POST)
    ResponseEntity<LikePostResponse> likePost(@Valid @RequestBody LikePostRequest likePostRequest);

    //Checked
    @DeleteMapping(POST)
    ResponseEntity<UnlikePostResponse> unlikePost(@Valid @RequestBody UnlikePostRequest unlikePostRequest);

    //Checked
    @PostMapping(COMMENT)
    ResponseEntity<LikeCommentResponse> likeComment(@Valid @RequestBody LikeCommentRequest likeCommentRequest);

    //Checked
    @DeleteMapping(COMMENT)
    ResponseEntity<UnlikeCommentResponse> unlikeComment(@Valid @RequestBody UnlikeCommentRequest unlikeCommentRequest);

    //Checked
    @GetMapping(POST + ID)
    ResponseEntity<PageImpl<UserMiniProfileResponse>> getLikesByPostId(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                                       @RequestParam("postId") @NotNull UUID postId, Pageable pageable);

    //Checked
    @GetMapping(COMMENT + ID)
    ResponseEntity<PageImpl<UserMiniProfileResponse>> getLikesByCommentId(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                                          @RequestParam("postId") @NotNull UUID postId,
                                                                          @RequestParam("commentId") @NotNull UUID commentId,
                                                                          Pageable pageable);
}