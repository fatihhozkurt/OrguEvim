package com.fatih.KnitShop.dto.request.like;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LikePostRequest(

        @NotNull
        UUID ownerId,

        @NotNull
        UUID postId,

        @NotNull
        UUID requesterId
) {
}
