package com.fatih.KnitShop.dto.request.post;

import com.fatih.KnitShop.dto.response.image.ImageResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreatePostRequest(

        @NotNull
        @NotBlank
        String postTitle,

        @NotNull
        @NotBlank
        String postIngredients,

        @NotNull
        @NotBlank
        String postContent,

        @NotNull
        UUID categoryId,

        @Nullable
        @NotBlank
        String youtubeLink,

        @NotEmpty
        List<ImageResponse> postImages,

        @NotNull
        UUID userId
) {
}