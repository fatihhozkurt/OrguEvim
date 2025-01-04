package com.fatih.KnitShop.dto.request.comment;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteReplyRequest(

        @NotNull
        UUID ownerId,

        @NotNull
        UUID postId,

        @NotNull
        UUID commentId,

        @NotNull
        UUID replyId,

        @NotNull
        UUID requesterId
) {
}
