package com.fatih.KnitShop.dto.request.user;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserUnfollowRequest(

        @NotNull
        UUID followerId,

        @NotNull
        UUID followingId
) {
}
