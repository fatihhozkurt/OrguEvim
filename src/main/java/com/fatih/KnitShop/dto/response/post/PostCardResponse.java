package com.fatih.KnitShop.dto.response.post;

import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import com.fatih.KnitShop.dto.response.image.ImageResponse;
import com.fatih.KnitShop.dto.response.user.UserMiniProfile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PostCardResponse(

        //Post fields
        UUID postId,
        LocalDateTime createTime,
        String postTitle,

        //User fields
        UserMiniProfile userMiniProfile,

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
