package com.fatih.KnitShop.dto.response.image;

import java.util.UUID;

public record ImageResponse(

        UUID imageId,
        String imagePath
) {
}