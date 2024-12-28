package com.fatih.KnitShop.dto.request.like;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LikeCommentRequest(

        @NotNull
        UUID ownerId,

        @NotNull
        UUID postId,

        @NotNull
        UUID commentId,

        @NotNull
        UUID userId
) {
}
