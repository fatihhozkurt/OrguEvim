package com.fatih.KnitShop.mapper;

import com.fatih.KnitShop.dto.request.post.CreatePostRequest;
import com.fatih.KnitShop.dto.request.post.UpdatePostRequest;
import com.fatih.KnitShop.dto.response.post.PostCardResponse;
import com.fatih.KnitShop.dto.response.post.PostDetailResponse;
import com.fatih.KnitShop.dto.response.post.PostSliderResponse;
import com.fatih.KnitShop.dto.response.post.UserProfilePostCardResponse;
import com.fatih.KnitShop.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ImageMapper.class, UserMapper.class, CategoryMapper.class})
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "postId", source = "id")
    @Mapping(target = "postTitle", source = "title")
    @Mapping(target = "userMiniProfileResponse", source = "user")
    @Mapping(target = "categoryResponse", source = "category")
    @Mapping(target = "coverImage", source = "coverImage")
    PostCardResponse toPostCardResponse(PostEntity postEntity);

    List<PostCardResponse> toPostCardResponseList(List<PostEntity> postEntities);

    @Mapping(target = "postId", source = "id")
    @Mapping(target = "postTitle", source = "title")
    @Mapping(target = "coverImage", source = "coverImage")
    @Mapping(target = "userMiniProfileResponse", source = "user")
    PostSliderResponse toPostSliderResponse(PostEntity postEntity);

    List<PostSliderResponse> toPostSliderResponseList(List<PostEntity> postEntities);

    @Mapping(target = "postId", source = "id")
    @Mapping(target = "postTitle", source = "title")
    @Mapping(target = "userMiniProfileResponse", source = "user")
    @Mapping(target = "postContent", source = "content")
    @Mapping(target = "categoryResponse", source = "category")
    @Mapping(target = "coverImage", source = "coverImage")
    @Mapping(target = "postImages", source = "images")
    PostDetailResponse toPostDetailResponse(PostEntity postEntity);

    @Mapping(target = "postId", source = "id")
    @Mapping(target = "postTitle", source = "title")
    @Mapping(target = "categoryResponse", source = "category")
    @Mapping(target = "coverImage", source = "coverImage")
    UserProfilePostCardResponse toUserProfilePostCardResponse(PostEntity postEntity);

    List<UserProfilePostCardResponse> toUserProfilePostCardResponseList(List<PostEntity> postEntities);

    @Mapping(target = "title", source = "postTitle")
    @Mapping(target = "ingredients", source = "postIngredients")
    @Mapping(target = "content", source = "postContent")
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "images", source = "postImages")
    @Mapping(target = "user.id", source = "ownerId")
    PostEntity createPostRequestToEntity(CreatePostRequest createPostRequest);

    @Mapping(target = "title", source = "postTitle")
    @Mapping(target = "ingredients", source = "postIngredients")
    @Mapping(target = "content", source = "postContent")
    @Mapping(target = "images", source = "postImages")
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "user.id", source = "ownerId")
    PostEntity updatePostRequestToEntity(UpdatePostRequest updatePostRequest);
}