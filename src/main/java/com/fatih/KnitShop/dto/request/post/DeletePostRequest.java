package com.fatih.KnitShop.dto.request.post;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeletePostRequest(

        @NotNull
        UUID ownerId,

        @NotNull
        UUID postId,

        @NotNull
        UUID userId
) {
}
