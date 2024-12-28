package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.api.UserControllerApi;
import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.dto.response.user.UserProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.fatih.KnitShop.url.UrlConst.USER;

@RestController
public class UserController implements UserControllerApi {
    @Override
    public ResponseEntity<UserProfileResponse> getUserById(UUID userId) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> followUser(UserFollowRequest userFollowRequest) {
        return null;
    }
}
