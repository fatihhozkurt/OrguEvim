package com.fatih.KnitShop.controller.api;

import com.fatih.KnitShop.dto.request.user.CreateUserRequest;
import com.fatih.KnitShop.dto.request.user.UpdateUserRequest;
import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.dto.request.user.UserUnfollowRequest;
import com.fatih.KnitShop.dto.response.user.UserMiniProfileResponse;
import com.fatih.KnitShop.dto.response.user.UserResponse;
import com.fatih.KnitShop.dto.response.user.UserProfileResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.fatih.KnitShop.url.UrlConst.*;

@RequestMapping(USER)
public interface UserControllerApi {

    //Checked
    @GetMapping(ID)
    ResponseEntity<UserProfileResponse> getUserById(@RequestParam("userId") @NotNull UUID userId);

    //Checked
    @PostMapping(FOLLOW)
    ResponseEntity<HttpStatus> followUser(@Valid @RequestBody UserFollowRequest userFollowRequest);

    //Checked
    @DeleteMapping(UNFOLLOW)
    ResponseEntity<HttpStatus> unfollowUser(@Valid @RequestBody UserUnfollowRequest userUnfollowRequest);

    //Checked
    @GetMapping(ALL)
    ResponseEntity<List<UserProfileResponse>> getAllUsers();

    //Checked
    @PostMapping
    ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest);

    //Checked
    @PutMapping
    ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest);

    //Checked
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteUser(@RequestParam("userId") @NotNull UUID userId);

    //Checked
    @GetMapping((FOLLOWERS))
    ResponseEntity<PageImpl<UserMiniProfileResponse>> getFollowers(@RequestParam("userId") @NotNull UUID userId,
                                                                   Pageable pageable);
    //Checked
    @GetMapping(FOLLOWINGS)
    ResponseEntity<PageImpl<UserMiniProfileResponse>> getFollowings(@RequestParam("userId") @NotNull UUID userId,
                                                                    Pageable pageable);
}