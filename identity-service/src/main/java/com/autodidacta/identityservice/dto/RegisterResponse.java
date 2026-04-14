package com.autodidacta.identityservice.dto;

import java.util.UUID;

public record RegisterResponse(
        UUID userId,
        String firstname,
        String lastname,
        String email,
        String phoneNumber
) {
}
