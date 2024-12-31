package com.fatih.KnitShop.dto.response.user;

import com.fatih.KnitShop.dto.response.image.ImageResponse;

import java.util.UUID;

public record UserMiniProfileResponse(

        UUID ownerId,
        String name,
        String surname,
        ImageResponse avatarImage
) {
}
