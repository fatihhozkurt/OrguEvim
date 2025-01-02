package com.fatih.KnitShop.dto.response.like;

import java.util.UUID;

public record LikePostResponse(

        //Like fields
        UUID likeId,
        Long currentPostLikeCount
) {
}
