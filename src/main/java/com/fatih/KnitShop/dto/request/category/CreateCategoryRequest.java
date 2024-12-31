package com.fatih.KnitShop.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(

        @NotNull
        @NotBlank
        @Size(min = 3, max = 25)
        String categoryName
) {
}
