package com.autodidacta.identityservice.dto;

import com.autodidacta.identityservice.entity.Role;

import java.util.UUID;

public record LoginResponse(
        String token,
        UUID userId,
        String firstname,
        String lastname,
        Role role
) {
}
