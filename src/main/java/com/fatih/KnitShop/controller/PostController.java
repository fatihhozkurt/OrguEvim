package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.PostControllerApi;
import com.fatih.KnitShop.dto.request.post.CreatePostRequest;
import com.fatih.KnitShop.dto.request.post.DeletePostRequest;
import com.fatih.KnitShop.dto.request.post.UpdatePostRequest;
import com.fatih.KnitShop.dto.response.post.PostCardResponse;
import com.fatih.KnitShop.dto.response.post.PostDetailResponse;
import com.fatih.KnitShop.dto.response.post.PostSliderResponse;
import com.fatih.KnitShop.dto.response.post.UserProfilePostCardResponse;
import com.fatih.KnitShop.manager.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PostController implements PostControllerApi {

    private final PostService postService;

    @Override
    public ResponseEntity<PageImpl<PostSliderResponse>> getRandomPosts(Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<PageImpl<PostCardResponse>> getAllPosts(Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<PostDetailResponse> getPostById(UUID ownerId, UUID postId) {
        return null;
    }

    @Override
    public ResponseEntity<PageImpl<UserProfilePostCardResponse>> getPostsByUserId(UUID ownerId, Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> deletePost(DeletePostRequest deletePostRequest) {
        return null;
    }

    @Override
    public ResponseEntity<PostCardResponse> getPostsByCategoryId(UUID categoryId) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> createPost(CreatePostRequest createPostRequest) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> updatePost(UpdatePostRequest updatePostRequest) {
        return null;
    }
}
