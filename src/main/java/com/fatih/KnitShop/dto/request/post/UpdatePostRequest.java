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
        String postTitle,

        @Nullable
        String postIngredients,

        @Nullable
        String postContent,

        @Nullable
        UUID categoryId,

        @Nullable
        @NotBlank
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
