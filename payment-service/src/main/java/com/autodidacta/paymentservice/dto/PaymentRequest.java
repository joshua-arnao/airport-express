package com.autodidacta.paymentservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        UUID bookingId,
        BigDecimal amount
) {
}
