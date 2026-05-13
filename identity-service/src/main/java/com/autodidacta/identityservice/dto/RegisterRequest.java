package com.autodidacta.identityservice.dto;

import com.autodidacta.identityservice.entity.DocumentType;

public record RegisterRequest(
        String firstName,
        String lastName,
        DocumentType documentType,
        String documentNumber,
        String email,
        String phoneNumber,
        String password
) {
}
