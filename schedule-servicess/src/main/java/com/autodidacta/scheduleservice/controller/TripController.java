package com.autodidacta.scheduleservice.controller;

import com.autodidacta.scheduleservice.dto.TripRequest;
import com.autodidacta.scheduleservice.dto.TripResponse;
import com.autodidacta.scheduleservice.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;

    @PostMapping
    public TripResponse createTrip(@Valid @RequestBody TripRequest tripRequest) {
        return tripService.createTrip(tripRequest);
    }

    @GetMapping("/date/{date}")
    public List<TripResponse> getTripForDate(@PathVariable LocalDate date) {
        return tripService.getTripForDate(date);
    }

    @GetMapping("/date/{date}/available")
    public List<TripResponse> getTripForDateForState(@PathVariable LocalDate date) {
        return tripService.getAvailableTripsByDate(date);
    }

    @PutMapping("/{tripId}")
    public TripResponse bookSeats(@PathVariable UUID tripId, @RequestBody Integer quantity) {
        return tripService.bookSeats(tripId, quantity);
    }

    @GetMapping("/{tripId}")
    public TripResponse getTripById(@PathVariable UUID tripId) {
        return tripService.getTripById(tripId);
    }
}
