package com.fatih.KnitShop.dto.request.user;

import com.fatih.KnitShop.dto.request.image.UploadImageRequest;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record CreateUserRequest(

        @NotNull
        @NotBlank
        @Size(min = 2, max = 50)
        String name,

        @NotNull
        @NotBlank
        @Size(min = 2, max = 50)
        String surname,

        @NotNull
        @NotBlank
        @Pattern(regexp = "^[a-z0-9]+$")
        @Size(min = 5, max = 33)
        String username,

        @NotNull
        @NotBlank
        @Email
        @Size(min = 13, max = 345)
        String email,

        @NotNull
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-ZÇĞİÖŞÜ])(?=.*[a-zçğıöşü])(?=.*[0-9]).*$")
        @Size(min = 8, max = 16)
        String password,

        @Nullable
        @NotBlank
        @Size(min = 1, max = 500)
        String biography,

        @Valid
        UploadImageRequest userAvatar
) {
}