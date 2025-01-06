package com.fatih.KnitShop.mapper;

import com.fatih.KnitShop.dto.response.like.*;
import com.fatih.KnitShop.entity.LikeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LikeMapper {

    LikeMapper INSTANCE = Mappers.getMapper(LikeMapper.class);

    @Mapping(target = "likeId", source = "id")
    @Mapping(target = "currentPostLikeCount", source = "post.likeCount")
    LikePostResponse toLikePostResponse(LikeEntity likeEntity);

    @Mapping(target = "currentPostLikeCount", source = "post.likeCount")
    UnlikePostResponse toUnlikePostResponse(LikeEntity likeEntity);

    @Mapping(target = "likeId", source = "id")
    @Mapping(target = "currentCommentLikeCount", source = "comment.likeCount")
    LikeCommentResponse toLikeCommentResponse(LikeEntity likeEntity);

    @Mapping(target = "currentCommentLikeCount", source = "comment.likeCount")
    UnlikeCommentResponse toUnlikeCommentResponse(LikeEntity likeEntity);

    @Mapping(target = "likeId", source = "id")
    @Mapping(target = "currentReplyLikeCount", source = "comment.likeCount")
    LikeReplyResponse toLikeReplyResponse(LikeEntity likeEntity);

    @Mapping(target = "currentReplyLikeCount", source = "comment.likeCount")
    UnlikeReplyResponse toUnlikeReplyResponse(LikeEntity likeEntity);
}
