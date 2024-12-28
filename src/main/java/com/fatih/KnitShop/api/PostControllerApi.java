package com.fatih.KnitShop.api;

import com.fatih.KnitShop.dto.request.post.CreatePostRequest;
import com.fatih.KnitShop.dto.request.post.DeletePostRequest;
import com.fatih.KnitShop.dto.request.post.UpdatePostRequest;
import com.fatih.KnitShop.dto.response.post.PostCardResponse;
import com.fatih.KnitShop.dto.response.post.PostDetailResponse;
import com.fatih.KnitShop.dto.response.post.PostSliderResponse;
import com.fatih.KnitShop.dto.response.post.UserProfilePostCardResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.fatih.KnitShop.url.UrlConst.*;

@RequestMapping(POST)
public interface PostControllerApi {

    @GetMapping(RANDOM)
    ResponseEntity<PageImpl<PostSliderResponse>> getRandomPosts(Pageable pageable);

    @GetMapping(ALL)
    ResponseEntity<PageImpl<PostCardResponse>> getAllPosts(Pageable pageable);

    @GetMapping(ID)
    ResponseEntity<PostDetailResponse> getPostById(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                   @RequestParam("postId") @NotNull UUID postId);

    @GetMapping(USER + ID)
    ResponseEntity<PageImpl<UserProfilePostCardResponse>> getPostsByUserId(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                                           Pageable pageable);

    @DeleteMapping
    ResponseEntity<HttpStatus> deletePost(@Valid @RequestBody DeletePostRequest deletePostRequest);

    @GetMapping(CATEGORY + ID)
    ResponseEntity<PostCardResponse> getPostsByCategoryId(@RequestParam("categoryId") @NotNull UUID categoryId);

    @PostMapping
    ResponseEntity<HttpStatus> createPost(@Valid @RequestBody CreatePostRequest createPostRequest);

    @PutMapping
    ResponseEntity<HttpStatus> updatePost(@Valid @RequestBody UpdatePostRequest updatePostRequest);
}