package com.fatih.KnitShop.dto.response.post;

import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import com.fatih.KnitShop.dto.response.image.ImageResponse;
import com.fatih.KnitShop.dto.response.user.UserMiniProfileResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDetailResponse(

        //Post fields
        UUID postId,
        LocalDateTime createTime,
        String postTitle,
        String postContent,
        String youtubeLink,

        //User fields
        UserMiniProfileResponse userMiniProfileResponse,

        //Category fields
        CategoryResponse categoryResponse,

        //Image fields
        ImageResponse coverImage,
        List<ImageResponse> postImages,

        //Like fields
        Long likeCount,

        //Comment fields
        Long commentCount
) {
}
