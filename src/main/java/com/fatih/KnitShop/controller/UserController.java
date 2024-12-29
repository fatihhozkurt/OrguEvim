package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.UserControllerApi;
import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.dto.request.user.UserUnfollowRequest;
import com.fatih.KnitShop.dto.response.user.UserProfileResponse;
import com.fatih.KnitShop.entity.UserEntity;
import com.fatih.KnitShop.manager.service.UserService;
import com.fatih.KnitShop.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<UserProfileResponse> getUserById(UUID userId) {
        UserEntity user = userService.getUserById(userId);
        UserProfileResponse response = UserMapper.INSTANCE.toUserProfileResponse(user);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> followUser(UserFollowRequest userFollowRequest) {
        userService.follow(userFollowRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<HttpStatus> unfollowUser(UserUnfollowRequest userUnfollowRequest) {
        userService.unfollow(userUnfollowRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        List<UserProfileResponse> userProfileResponses = UserMapper.INSTANCE.toUserProfileResponseList(users);
        return new ResponseEntity<>(userProfileResponses, HttpStatus.OK);
    }
}
