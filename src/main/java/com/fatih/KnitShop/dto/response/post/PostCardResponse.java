package com.fatih.KnitShop.dto.response.post;

import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import com.fatih.KnitShop.dto.response.image.ImageResponse;
import com.fatih.KnitShop.dto.response.user.UserMiniProfileResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostCardResponse(

        //Post fields
        UUID postId,
        LocalDateTime createTime,
        String postTitle,

        //User fields
        UserMiniProfileResponse userMiniProfileResponse,

        //Category fields
        CategoryResponse categoryResponse,

        //Image fields
        ImageResponse coverImage,

        //Like fields
        Long likeCount,

        //Comment fields
        Long commentCount
) {
}
