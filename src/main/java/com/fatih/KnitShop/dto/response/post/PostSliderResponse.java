package com.fatih.KnitShop.dto.response.post;

import com.fatih.KnitShop.dto.response.image.ImageResponse;
import com.fatih.KnitShop.dto.response.user.UserMiniProfileResponse;

import java.util.UUID;

public record PostSliderResponse(

        //Post fields
        UUID postId,
        String postTitle,

        //Image fields
        ImageResponse coverImage,

        //User fields
        UserMiniProfileResponse userMiniProfileResponse
) {
}
