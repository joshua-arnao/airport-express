package com.autodidacta.identityservice.dto;

import java.util.UUID;

public record RegisterResponse(
        UUID userId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
