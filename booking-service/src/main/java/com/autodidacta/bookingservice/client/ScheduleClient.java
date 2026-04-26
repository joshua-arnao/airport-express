package com.autodidacta.bookingservice.client;

import com.autodidacta.scheduleservice.dto.TripResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "schedule-servicess")
public interface ScheduleClient {
    @GetMapping("/api/trips/{tripId}")
    TripResponse getTripById(@PathVariable UUID tripId);
}
