package com.autodidacta.paymentservice.dto;

import java.util.UUID;

public record PaymentResponse (
        String paymentStatus,
        UUID bookingId
){
}
