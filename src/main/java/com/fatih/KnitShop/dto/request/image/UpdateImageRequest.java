package com.fatih.KnitShop.dto.request.image;

import com.fatih.KnitShop.annotation.OptionalFieldValidation;

public record UpdateImageRequest(

        @OptionalFieldValidation(notBlank = true, min = 10, max = 500)
        String imagePath
) {
}
