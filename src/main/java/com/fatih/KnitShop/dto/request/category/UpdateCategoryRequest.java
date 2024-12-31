package com.fatih.KnitShop.dto.request.category;

import com.fatih.KnitShop.annotation.OptionalFieldValidation;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateCategoryRequest(

        @NotNull
        UUID categoryId,

        @OptionalFieldValidation(notBlank = true, min = 3, max = 20)
        String categoryName
) {
}
