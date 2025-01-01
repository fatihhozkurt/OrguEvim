package com.fatih.KnitShop.dto.request.image;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UploadImageRequest(

        @NotNull
        @NotBlank
        @Size(min = 10, max = 500)
        String imagePath
) {
}
