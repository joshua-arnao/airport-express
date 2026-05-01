package com.autodidacta.paymentservice.service;

import com.autodidacta.paymentservice.dto.PaymentRequest;
import com.autodidacta.paymentservice.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
    PaymentResponse refundPayment(String stripePaymentId);
}
