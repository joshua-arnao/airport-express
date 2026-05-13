package com.autodidacta.paymentservice.client;

import com.autodidacta.paymentservice.dto.BookingClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "booking-service", url = "http://localhost:8083")
public interface BookingClient {
    @PutMapping("/api/bookings/{bookingId}/confirm")
    BookingClientResponse confirmBooking(@PathVariable UUID bookingId);
}
