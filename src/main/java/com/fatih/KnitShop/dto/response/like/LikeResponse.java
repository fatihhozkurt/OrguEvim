package com.fatih.KnitShop.dto.response.like;

import java.util.UUID;

public record LikeResponse(

        //Like fields
        UUID likeId,
        Long currentLikeCount
) {
}
