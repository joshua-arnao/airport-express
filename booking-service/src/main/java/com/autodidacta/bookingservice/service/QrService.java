package com.autodidacta.bookingservice.service;

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

    public String generateQrToken(UUID ticketId, UUID tripId, UUID stopId) {
        return Jwts.builder()
                .claim("ticketId", ticketId.toString())
                .claim("tripId", tripId.toString())
                .claim("stopId", stopId.toString())
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(qrSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}