package com.fatih.KnitShop.mapper;

import com.fatih.KnitShop.dto.response.post.PostCardResponse;
import com.fatih.KnitShop.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostCardResponse toPostCardResponse(PostEntity postEntity);
    List<PostCardResponse> toPostCardResponseList(List<PostEntity> postEntities);
}
