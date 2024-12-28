package com.fatih.KnitShop.dto.response.category;

import java.util.UUID;

public record CategoryResponse(

        UUID categoryId,
        String categoryName
) {
}
