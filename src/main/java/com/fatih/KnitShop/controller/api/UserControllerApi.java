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

import static com.fatih.KnitShop.consts.UrlConst.*;

@RequestMapping(USER)
public interface UserControllerApi {

    //CheckedX
    @GetMapping(ID)
    ResponseEntity<UserProfileResponse> getUserById(@RequestParam("userId") @NotNull UUID userId);

    //CheckedX
    @PostMapping(FOLLOW)
    ResponseEntity<HttpStatus> followUser(@Valid @RequestBody UserFollowRequest userFollowRequest);

    //CheckedX
    @DeleteMapping(UNFOLLOW)
    ResponseEntity<HttpStatus> unfollowUser(@Valid @RequestBody UserUnfollowRequest userUnfollowRequest);

    //CheckedX
    @GetMapping(ALL)
    ResponseEntity<List<UserProfileResponse>> getAllUsers();

    //CheckedX
    @PostMapping
    ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest);

    //CheckedX
    @PutMapping
    ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest,
                                            @RequestParam("requesterId") @NotNull UUID requesterId);

    //CheckedX
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteUser(@RequestParam("ownerId") @NotNull UUID ownerId,
                                          @RequestParam("requesterId") @NotNull UUID requesterId);

    //CheckedX
    @GetMapping((FOLLOWERS))
    ResponseEntity<PageImpl<UserMiniProfileResponse>> getFollowers(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                                   Pageable pageable);

    //CheckedX
    @GetMapping(FOLLOWINGS)
    ResponseEntity<PageImpl<UserMiniProfileResponse>> getFollowings(@RequestParam("ownerId") @NotNull UUID ownerId,
                                                                    Pageable pageable);
}