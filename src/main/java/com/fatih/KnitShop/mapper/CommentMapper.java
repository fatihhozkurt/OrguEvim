package com.fatih.KnitShop.mapper;

import com.fatih.KnitShop.dto.response.comment.CommentResponse;
import com.fatih.KnitShop.dto.response.comment.ReplyResponse;
import com.fatih.KnitShop.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UserMapper.class, CommentMapper.class})
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "commentId", source = "id")
    @Mapping(target = "commentCount", source = "post.commentCount")
    @Mapping(target = "commentContent", source = "content")
    @Mapping(target = "userMiniProfileResponse", source = "user")
    CommentResponse toCommentResponse(CommentEntity commentEntity);

    List<CommentResponse> toCommentResponseList(List<CommentEntity> commentEntities);

    @Mapping(target = "replyId", source = "id")
    @Mapping(target = "replyContent", source = "content")
    @Mapping(target = "replyCount", source = "supComment.replyCount")
    @Mapping(target = "userMiniProfileResponse", source = "user")
    ReplyResponse toReplyResponse(CommentEntity commentEntity);

    List<ReplyResponse> toReplyResponseList(List<CommentEntity> commentEntities);
}
