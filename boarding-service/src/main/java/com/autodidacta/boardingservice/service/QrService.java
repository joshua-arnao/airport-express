package com.autodidacta.boardingservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.UUID;

@Service
public class QrService {

    @Value("${qr.secret}")
    private String qrSecret;

    public UUID extractTicketId(String qrToken) {
        String ticketId = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(qrToken)
                .getPayload()
                .get("ticketId", String.class);
        return UUID.fromString(ticketId);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(qrSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
