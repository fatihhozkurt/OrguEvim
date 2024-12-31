package com.fatih.KnitShop.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateCommentRequest(

        @NotNull
        UUID ownerId,

        @NotNull
        UUID postId,

        @NotNull
        @NotBlank
        @Size(min = 100, max = 2200)
        String content,

        @NotNull
        UUID userId
) {
}
