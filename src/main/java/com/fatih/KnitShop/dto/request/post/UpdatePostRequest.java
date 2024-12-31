package com.fatih.KnitShop.dto.request.post;

import com.fatih.KnitShop.dto.response.image.ImageResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record UpdatePostRequest(

        @Nullable
        @Size(min = 5, max = 100)
        String postTitle,

        @Nullable
        @Size(min = 20, max = 2000)
        String postIngredients,

        @Size(min = 100, max = 3000)
        @Nullable
        String postContent,

        @Nullable
        UUID categoryId,

        @Nullable
        @NotBlank
        @Size(min = 5, max = 100)
        String youtubeLink,

        @Nullable
        @Size(min = 1)
        List<ImageResponse> postImages,

        @NotNull
        UUID postId,

        @NotNull
        UUID userId
) {
}
