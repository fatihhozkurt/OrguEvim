package com.fatih.KnitShop.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateReplyRequest(

        @NotNull
        UUID ownerId,

        @NotNull
        UUID postId,

        @NotNull
        UUID commentId,

        @NotNull
        @NotBlank
        String content,

        @NotNull
        UUID userId
) {
}
