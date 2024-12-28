package com.fatih.KnitShop.dto.request.like;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UnlikeReplyRequest(

        @NotNull
        UUID ownerId,

        @NotNull
        UUID postId,

        @NotNull
        UUID commentId,

        @NotNull
        UUID replyId,

        @NotNull
        UUID likeId,

        @NotNull
        UUID userId
) {
}
