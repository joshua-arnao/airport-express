package com.autodidacta.identityservice.shared.error;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String error,
        LocalDateTime timestamp
) {
}
