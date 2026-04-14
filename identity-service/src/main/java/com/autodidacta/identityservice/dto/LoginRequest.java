package com.autodidacta.identityservice.dto;

public record LoginRequest(
        String identifier,
        String password
) {
}
