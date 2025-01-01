package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.UserControllerApi;
import com.fatih.KnitShop.dto.request.user.CreateUserRequest;
import com.fatih.KnitShop.dto.request.user.UpdateUserRequest;


import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.dto.request.user.UserUnfollowRequest;
import com.fatih.KnitShop.dto.response.user.UserMiniProfileResponse;
import com.fatih.KnitShop.dto.response.user.UserResponse;
import com.fatih.KnitShop.dto.response.user.UserProfileResponse;
import com.fatih.KnitShop.entity.UserEntity;
import com.fatih.KnitShop.manager.service.UserService;
import com.fatih.KnitShop.mapper.UserMapper;
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
public class UserController implements UserControllerApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> createUser(CreateUserRequest createUserRequest) {

        UserEntity user = UserMapper.INSTANCE.createUserRequestToUserEntity(createUserRequest);
        UserEntity savedUser = userService.createUser(user);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(savedUser);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(UpdateUserRequest updateUserRequest, UUID requesterId) {

        UserEntity user = UserMapper.INSTANCE.updateUserRequestToUserEntity(updateUserRequest);
        UserEntity updatedUser = userService.updateUser(user, requesterId);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(updatedUser);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteUser(UUID ownerId, UUID requesterId) {

        userService.deleteUser(ownerId, requesterId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<PageImpl<UserMiniProfileResponse>> getFollowers(UUID ownerId, Pageable pageable) {

        Page<UserEntity> foundFollowers = userService.getFollowersById(ownerId, pageable);
        List<UserMiniProfileResponse> userMiniProfileResponses
                = UserMapper.INSTANCE.toUserMiniProfileResponseList(foundFollowers.getContent());

        return new ResponseEntity<>(new PageImpl<>(userMiniProfileResponses,
                pageable,
                foundFollowers.getTotalElements()),
                HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<PageImpl<UserMiniProfileResponse>> getFollowings(UUID ownerId, Pageable pageable) {

        Page<UserEntity> foundFollowings = userService.getFollowingsById(ownerId, pageable);
        List<UserMiniProfileResponse> userMiniProfileResponses
                = UserMapper.INSTANCE.toUserMiniProfileResponseList(foundFollowings.getContent());

        return new ResponseEntity<>(new PageImpl<>(userMiniProfileResponses,
                pageable,
                foundFollowings.getTotalElements()),
                HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<UserProfileResponse> getUserById(UUID userId) {
        UserEntity foundUser = userService.getUserById(userId);
        UserProfileResponse response = UserMapper.INSTANCE.toUserProfileResponse(foundUser);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> followUser(UserFollowRequest userFollowRequest) {
        userService.follow(userFollowRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<HttpStatus> unfollowUser(UserUnfollowRequest userUnfollowRequest) {
        userService.unfollow(userUnfollowRequest.unfollowerId(),
                userUnfollowRequest.unfollowingId(),
                userUnfollowRequest.requesterId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        List<UserEntity> foundUsers = userService.getAllUsers();
        List<UserProfileResponse> userProfileResponses = UserMapper.INSTANCE.toUserProfileResponseList(foundUsers);
        return new ResponseEntity<>(userProfileResponses, HttpStatus.OK);
    }
}
