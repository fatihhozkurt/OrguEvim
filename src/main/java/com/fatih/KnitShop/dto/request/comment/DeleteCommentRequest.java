package com.fatih.KnitShop.dto.request.comment;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteCommentRequest(

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
