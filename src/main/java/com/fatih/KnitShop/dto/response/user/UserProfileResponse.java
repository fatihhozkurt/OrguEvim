package com.fatih.KnitShop.dto.response.user;

import com.fatih.KnitShop.dto.response.image.ImageResponse;

import java.util.UUID;

public record UserProfileResponse(

        //User fields
        UUID userId,
        String name,
        String surname,
        String userName,
        Long followersCount,
        Long followingCount,
        String biography,

        //Image fields
        ImageResponse avatarImage,

        //Post fields
        Long postCount
) {
}
