package com.fatih.KnitShop.mapper;

import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.dto.response.user.UserProfileResponse;
import com.fatih.KnitShop.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ImageMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "avatarImage.imagePath", source = "avatarImage.imagePath")
    UserProfileResponse toUserProfileResponse(UserEntity userEntity);
}
