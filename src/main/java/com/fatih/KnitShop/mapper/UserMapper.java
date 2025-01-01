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

@Mapper(uses = {ImageMapper.class, CategoryMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //Checked
    @Mapping(target = "userId", source = "id")
    @Mapping(target = "avatarImage", source = "avatarImage")
    UserProfileResponse toUserProfileResponse(UserEntity userEntity);

    //Checked
    List<UserProfileResponse> toUserProfileResponseList(List<UserEntity> userEntities);

    //Checked
    @Mapping(target = "avatarImage", source = "userAvatar")
    UserEntity createUserRequestToUserEntity(CreateUserRequest createUserRequest);

    //Checked
    @Mapping(target = "id", source = "ownerId")
    @Mapping(target = "avatarImage", source = "userAvatar")
    UserEntity updateUserRequestToUserEntity(UpdateUserRequest updateUserRequest);

    //Checked
    @Mapping(target = "userId", source = "id")
    @Mapping(target = "imageResponse", source = "avatarImage")
    UserResponse toUserResponse(UserEntity userEntity);

    //Checked
    @Mapping(target = "ownerId", source = "id")
    @Mapping(target = "avatarImage", source = "avatarImage")
    UserMiniProfileResponse toUserMiniProfileResponse(UserEntity userEntity);

    //Checked
    List<UserMiniProfileResponse> toUserMiniProfileResponseList(List<UserEntity> userEntities);
}
