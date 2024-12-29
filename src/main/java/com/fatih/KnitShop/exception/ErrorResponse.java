package com.fatih.KnitShop.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        Integer errorCode,
        String message,
        LocalDateTime dateTime
) {
}
