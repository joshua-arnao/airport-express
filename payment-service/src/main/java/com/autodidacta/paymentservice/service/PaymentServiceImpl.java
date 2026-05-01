package com.autodidacta.paymentservice.service;

import com.autodidacta.paymentservice.client.BookingClient;
import com.autodidacta.paymentservice.dto.PaymentRequest;
import com.autodidacta.paymentservice.dto.PaymentResponse;
import com.autodidacta.paymentservice.shared.exceptions.PaymentException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    private final BookingClient bookingClient;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        try {
            Stripe.apiKey = stripeSecretKey;

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(paymentRequest.amount()
                            .multiply(BigDecimal.valueOf(100)).longValue())
                    .setCurrency("usd")
                    .setDescription("Airport Express Lima - Booking: " + paymentRequest.bookingId())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            bookingClient.confirmBooking(paymentRequest.bookingId());

            return new PaymentResponse(
                    paymentIntent.getStatus(),
                    paymentRequest.bookingId()
            );

        } catch (StripeException e) {
            throw new PaymentException("Payment failed: " + e.getMessage());
        }
    }

    @Override
    public PaymentResponse refundPayment(String stripePaymentId) {
        try {
            Stripe.apiKey = stripeSecretKey;
            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(stripePaymentId)
                    .build();

            Refund refund = Refund.create(params);

            return new PaymentResponse(
                    refund.getStatus(),
                    null
            );

        } catch (StripeException e) {
            throw new PaymentException("Refund failed: " + e.getMessage());
        }
    }
}
