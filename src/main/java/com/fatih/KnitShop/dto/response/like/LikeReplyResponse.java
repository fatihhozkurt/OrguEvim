package com.fatih.KnitShop.dto.response.like;

import java.util.UUID;

public record LikeReplyResponse(
        UUID likeId,
        Long currentReplyLikeCount
) {
}
