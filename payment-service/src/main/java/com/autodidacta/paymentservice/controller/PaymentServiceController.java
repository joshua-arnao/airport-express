package com.autodidacta.paymentservice.controller;

import com.autodidacta.paymentservice.dto.PaymentRequest;
import com.autodidacta.paymentservice.dto.PaymentResponse;
import com.autodidacta.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentServiceController {
    private final PaymentService paymentService;

    @PostMapping("/process")
    public PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.processPayment(paymentRequest);
    }

    @PostMapping("/refund/{stripePaymentId}")
    public PaymentResponse refundPayment(@PathVariable String stripePaymentId) {
        return paymentService.refundPayment(stripePaymentId);
    }
}
