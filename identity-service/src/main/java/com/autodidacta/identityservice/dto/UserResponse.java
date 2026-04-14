package com.autodidacta.identityservice.dto;

import com.autodidacta.identityservice.entity.Role;

import java.util.UUID;

public record UserResponse(
        UUID userId,
        String firstname,
        String lastname,
        Role role,
        String email,
        String phoneNumber
) {
}
