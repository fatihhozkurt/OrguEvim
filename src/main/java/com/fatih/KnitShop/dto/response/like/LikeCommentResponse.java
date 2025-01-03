package com.fatih.KnitShop.dto.response.like;

import java.util.UUID;

public record LikeCommentResponse(

        //Like fields
        UUID likeId,
        Long currentCommentLikeCount
) {
}
