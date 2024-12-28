package com.fatih.KnitShop.dto.response.post;

import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import com.fatih.KnitShop.dto.response.image.ImageResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserProfilePostCardResponse(

        //Post fields
        UUID postId,
        String postTitle,
        LocalDateTime createTime,

        //Category fields
        CategoryResponse categoryResponse,

        //Image fields
        ImageResponse coverImage
) {
}