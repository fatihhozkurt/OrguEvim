package com.fatih.KnitShop.dto.request.post;

import com.fatih.KnitShop.dto.response.image.ImageResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreatePostRequest(

        @NotNull
        @NotBlank
        @Size(min = 5, max = 100)
        String postTitle,

        @NotNull
        @NotBlank
        @Size(min = 20, max = 2000)
        String postIngredients,

        @NotNull
        @NotBlank
        @Size(min = 100, max = 3000)
        String postContent,

        @NotNull
        UUID categoryId,

        @Nullable
        @NotBlank
        @Size(min = 5, max = 100)
        String youtubeLink,

        @NotEmpty
        @Size(min = 1)
        List<ImageResponse> postImages,

        @NotNull
        UUID userId
) {
}