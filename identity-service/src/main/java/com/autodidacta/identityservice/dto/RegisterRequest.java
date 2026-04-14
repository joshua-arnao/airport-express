package com.autodidacta.identityservice.dto;

import com.autodidacta.identityservice.entity.DocumentType;

public record RegisterRequest(
        String firstname,
        String lastname,
        DocumentType documentType,
        String documentNumber,
        String email,
        String phoneNumber,
        String password
) {
}
