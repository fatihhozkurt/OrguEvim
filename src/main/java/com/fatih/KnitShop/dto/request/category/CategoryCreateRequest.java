package com.fatih.KnitShop.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryCreateRequest(
        @NotNull
        @NotBlank
        String categoryName
) {
}
