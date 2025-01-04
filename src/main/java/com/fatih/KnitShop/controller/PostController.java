package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.PostControllerApi;
import com.fatih.KnitShop.dto.request.post.CreatePostRequest;
import com.fatih.KnitShop.dto.request.post.UpdatePostRequest;
import com.fatih.KnitShop.dto.response.post.PostCardResponse;
import com.fatih.KnitShop.dto.response.post.PostDetailResponse;
import com.fatih.KnitShop.dto.response.post.PostSliderResponse;
import com.fatih.KnitShop.dto.response.post.UserProfilePostCardResponse;
import com.fatih.KnitShop.entity.PostEntity;
import com.fatih.KnitShop.manager.service.PostService;
import com.fatih.KnitShop.mapper.PostMapper;
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
public class PostController implements PostControllerApi {

    private final PostService postService;

    @Override
    public ResponseEntity<PageImpl<PostSliderResponse>> getRandomPosts(Pageable pageable) {

        Page<PostEntity> foundPosts = postService.getRandomPosts(pageable);
        List<PostSliderResponse> postSliderResponses = PostMapper.INSTANCE.toPostSliderResponseList(foundPosts.getContent());
        return new ResponseEntity<>(new PageImpl<>(postSliderResponses,
                pageable,
                foundPosts.getTotalElements()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageImpl<PostCardResponse>> getAllPosts(Pageable pageable) {

        Page<PostEntity> foundPosts = postService.getAllPosts(pageable);
        List<PostCardResponse> postCardResponses = PostMapper.INSTANCE.toPostCardResponseList(foundPosts.getContent());

        return new ResponseEntity<>(new PageImpl<>(postCardResponses,
                pageable,
                foundPosts.getTotalElements()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostDetailResponse> getPostById(UUID ownerId, UUID postId) {

        PostEntity postEntity = postService.getPostById(ownerId, postId);
        PostDetailResponse postDetailResponse = PostMapper.INSTANCE.toPostDetailResponse(postEntity);

        return new ResponseEntity<>(postDetailResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<PageImpl<UserProfilePostCardResponse>> getPostsByUserId(UUID ownerId, Pageable pageable) {

        Page<PostEntity> postEntities = postService.getPostsByUserId(ownerId, pageable);
        List<UserProfilePostCardResponse> postCardResponses =
                PostMapper.INSTANCE.toUserProfilePostCardResponseList(postEntities.getContent());

        return new ResponseEntity<>(new PageImpl<>(postCardResponses,
                pageable,
                postEntities.getTotalElements()),
                HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<PageImpl<PostCardResponse>> getPostsByCategoryId(UUID categoryId, Pageable pageable) {

        Page<PostEntity> foundPosts = postService.getPostsByCategoryId(categoryId, pageable);
        List<PostCardResponse> postCardResponses = PostMapper.INSTANCE.toPostCardResponseList(foundPosts.getContent());

        return new ResponseEntity<>(new PageImpl<>(postCardResponses,
                pageable,
                foundPosts.getTotalElements()),
                HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deletePost(UUID ownerId, UUID postId, UUID requesterId) {

        postService.deletePost(ownerId, postId, requesterId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<PostDetailResponse> createPost(CreatePostRequest createPostRequest, UUID requesterId) {

        PostEntity postEntity = PostMapper.INSTANCE.createPostRequestToEntity(createPostRequest);
        PostEntity savedPost = postService.createPost(postEntity, requesterId);
        PostDetailResponse postDetailResponse = PostMapper.INSTANCE.toPostDetailResponse(savedPost);

        return new ResponseEntity<>(postDetailResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PostDetailResponse> updatePost(UpdatePostRequest updatePostRequest, UUID requesterId) {

        PostEntity postEntity = PostMapper.INSTANCE.updatePostRequestToEntity(updatePostRequest);
        PostEntity updatedPost = postService.updatePost(postEntity, requesterId);
        PostDetailResponse postDetailResponse = PostMapper.INSTANCE.toPostDetailResponse(updatedPost);

        return new ResponseEntity<>(postDetailResponse, HttpStatus.OK);
    }
}