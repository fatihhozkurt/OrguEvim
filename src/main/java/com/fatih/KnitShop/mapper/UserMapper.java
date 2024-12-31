package com.fatih.KnitShop.mapper;

import com.fatih.KnitShop.dto.request.user.CreateUserRequest;
import com.fatih.KnitShop.dto.request.user.UpdateUserRequest;
import com.fatih.KnitShop.dto.response.user.UserMiniProfileResponse;
import com.fatih.KnitShop.dto.response.user.UserResponse;
import com.fatih.KnitShop.dto.response.user.UserProfileResponse;
import com.fatih.KnitShop.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ImageMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "avatarImage.imagePath", source = "avatarImage.imagePath")
    UserProfileResponse toUserProfileResponse(UserEntity userEntity);

    List<UserProfileResponse> toUserProfileResponseList(List<UserEntity> userEntities);

    @Mapping(target = "avatarImage.imagePath", source = "userAvatar")
    UserEntity createUserRequestToUserEntity(CreateUserRequest createUserRequest);

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "avatarImage.imagePath", source = "userAvatar")
    UserEntity updateUserRequestToUserEntity(UpdateUserRequest updateUserRequest);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "imageResponse.imagePath", source = "avatarImage.imagePath")
    UserResponse toUserResponse(UserEntity userEntity);

    @Mapping(target = "ownerId", source = "id")
    @Mapping(target = "avatarImage.imagePath", source = "avatarImage.imagePath")
    UserMiniProfileResponse toUserMiniProfileResponse(UserEntity userEntity);

    List<UserMiniProfileResponse> toUserMiniProfileResponseList(List<UserEntity> userEntities);

}
