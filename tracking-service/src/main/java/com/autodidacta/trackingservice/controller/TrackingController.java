package com.autodidacta.trackingservice.controller;

import com.autodidacta.trackingservice.dto.TrackingResponse;
import com.autodidacta.trackingservice.dto.TrackingUpdateRequest;
import com.autodidacta.trackingservice.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/trackings")
@RequiredArgsConstructor
public class TrackingController {
    private final TrackingService trackingService;

    @GetMapping("/{tripId}")
    public TrackingResponse getPosition(@PathVariable UUID tripId) {
        return trackingService.getPosition(tripId);
    }

    @PutMapping
    public TrackingResponse updatePosition(@RequestBody TrackingUpdateRequest request) {
        return trackingService.updatePosition(request);
    }


}
