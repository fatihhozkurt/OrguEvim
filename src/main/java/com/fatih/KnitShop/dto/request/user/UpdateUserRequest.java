package com.fatih.KnitShop.dto.request.user;

import com.fatih.KnitShop.annotation.OptionalFieldValidation;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateUserRequest(

        @NotNull
        UUID userId,

        @OptionalFieldValidation(notBlank = true, min = 2, max = 50)
        String name,

        @OptionalFieldValidation(notBlank = true, min = 2, max = 50)
        String surname,

        @OptionalFieldValidation(notBlank = true, min = 5, max = 33, pattern = "^[a-z0-9]+$")
        String username,

        @OptionalFieldValidation(notBlank = true, min = 13, max = 345, pattern = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
        String email,

        @OptionalFieldValidation(
                notBlank = true,
                min = 8,
                max = 16,
                pattern = "^(?=.*[A-ZÇĞİÖŞÜ])(?=.*[a-zçğıöşü])(?=.*[0-9]).*$"
        )
        String password,

        @OptionalFieldValidation(notBlank = true, min = 1, max = 500)
        String biography,

        @OptionalFieldValidation(notBlank = true, min = 10, max = 500)
        String userAvatar
) {
}
