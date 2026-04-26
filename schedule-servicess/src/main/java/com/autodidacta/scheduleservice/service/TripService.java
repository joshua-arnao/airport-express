package com.autodidacta.scheduleservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.autodidacta.scheduleservice.dto.TripRequest;
import com.autodidacta.scheduleservice.dto.TripResponse;

public interface TripService {
    TripResponse createTrip(TripRequest tripRequest);

    List<TripResponse> getTripForDate(LocalDate date);

    List<TripResponse> getAvailableTripsByDate(LocalDate date);

    TripResponse bookSeats(UUID tripId, Integer quantity);
}
