package com.fatih.KnitShop.controller.api;

import com.fatih.KnitShop.dto.request.post.CreatePostRequest;
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

    //Checked
    @GetMapping(RANDOM)
    ResponseEntity<PageImpl<PostSliderResponse>> getRandomPosts(Pageable pageable);

    //Checked
    @GetMapping(ALL)
    ResponseEntity<PageImpl<PostCardResponse>> getAllPosts(Pageable pageable);

    //Checked
    @GetMapping(ID)
    ResponseEntity<PostDetailResponse> getPostById(@RequestParam("ownerId") @NotNull UUID ownerId,

                                                   @RequestParam("postId") @NotNull UUID postId);

    //Checked
    @GetMapping(USER + ID)
    ResponseEntity<PageImpl<UserProfilePostCardResponse>> getPostsByUserId(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                                           Pageable pageable);

    //Checked
    @GetMapping(CATEGORY + ID)
    ResponseEntity<PageImpl<PostCardResponse>> getPostsByCategoryId(@RequestParam("categoryId") @NotNull UUID categoryId,
                                                                    Pageable pageable);

    //Checked
    @PostMapping
    ResponseEntity<PostDetailResponse> createPost(@Valid @RequestBody CreatePostRequest createPostRequest,
                                                  @RequestParam("userId") @NotNull UUID userId);

    //Checked
    @DeleteMapping
    ResponseEntity<HttpStatus> deletePost(@RequestParam("ownerId") @NotNull UUID ownerId,
                                          @RequestParam("postId") @NotNull UUID postId,
                                          @RequestParam("userId") @NotNull UUID userId);

    //Checked
    @PutMapping
    ResponseEntity<PostDetailResponse> updatePost(@Valid @RequestBody UpdatePostRequest updatePostRequest,
                                                  @RequestParam("userId") @NotNull UUID userId);

    @DeleteMapping(ALL)
    ResponseEntity<HttpStatus> deleteAllPosts();
}