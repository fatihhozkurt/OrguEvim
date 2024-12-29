package com.fatih.KnitShop.controller.api;

import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.dto.request.user.UserUnfollowRequest;
import com.fatih.KnitShop.dto.response.user.UserProfileResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.fatih.KnitShop.url.UrlConst.*;

@RequestMapping(USER)
public interface UserControllerApi {

    @GetMapping(ID)
    ResponseEntity<UserProfileResponse> getUserById(@RequestParam @NotNull UUID userId);

    @PostMapping(FOLLOW)
    ResponseEntity<HttpStatus> followUser(@Valid @RequestBody UserFollowRequest userFollowRequest);

    @DeleteMapping(UNFOLLOW)
    ResponseEntity<HttpStatus> unfollowUser(@Valid @RequestBody UserUnfollowRequest userUnfollowRequest);

    @GetMapping(ALL)
    ResponseEntity<List<UserProfileResponse>> getAllUsers();
}