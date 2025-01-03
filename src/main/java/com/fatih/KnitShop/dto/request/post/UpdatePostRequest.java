package com.fatih.KnitShop.dto.request.post;

import com.fatih.KnitShop.annotation.OptionalFieldValidation;
import com.fatih.KnitShop.dto.request.image.UploadImageRequest;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UpdatePostRequest(

        @NotNull
        UUID postId,

        @NotNull
        UUID ownerId,

        @OptionalFieldValidation(notBlank = true, min = 20, max = 2000)
        String postTitle,

        @OptionalFieldValidation(notBlank = true, min = 20, max = 2000)
        String postIngredients,

        @OptionalFieldValidation(notBlank = true, min = 100, max = 3000)
        String postContent,

        @OptionalFieldValidation(notBlank = true, min = 5, max = 100)
        String youtubeLink,

        @OptionalFieldValidation(min = 1)
        List<UploadImageRequest> postImages
) {
}
