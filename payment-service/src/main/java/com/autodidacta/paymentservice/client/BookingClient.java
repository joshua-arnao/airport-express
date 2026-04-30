package com.autodidacta.paymentservice.client;

import com.autodidacta.paymentservice.dto.BookingClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "booking-service")
public interface BookingClient {
    @PostMapping("/bookings/confirm/{bookingId}")
    BookingClientResponse confirmBooking(@PathVariable("bookingId") UUID bookingId);
}
