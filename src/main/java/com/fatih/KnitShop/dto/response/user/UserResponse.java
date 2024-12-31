package com.fatih.KnitShop.dto.response.user;

import com.fatih.KnitShop.dto.response.image.ImageResponse;

import java.util.UUID;

public record UserResponse(

        UUID userId,
        String name,
        String surname,
        String username,
        String password,
        String email,
        String biography,
        Long followersCount,
        Long followingCount,
        Long postCount,
        ImageResponse imageResponse
) {
}
