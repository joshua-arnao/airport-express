package com.autodidacta.bookingservice.client;

import com.autodidacta.bookingservice.dto.TripClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "schedule-service")
public interface ScheduleClient {
    @GetMapping("/api/trips/{tripId}")
    TripClientResponse getTripById(@PathVariable UUID tripId);
}
