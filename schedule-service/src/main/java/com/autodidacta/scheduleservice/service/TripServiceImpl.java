package com.autodidacta.scheduleservice.service;

import com.autodidacta.scheduleservice.dto.*;
import com.autodidacta.scheduleservice.entity.Schedule;
import com.autodidacta.scheduleservice.entity.Trip;
import com.autodidacta.scheduleservice.entity.TripStatus;
import com.autodidacta.scheduleservice.repository.RouteRepository;
import com.autodidacta.scheduleservice.repository.ScheduleRepository;
import com.autodidacta.scheduleservice.repository.TripRepository;
import com.autodidacta.scheduleservice.shared.exceptions.ScheduleNotFoundException;
import com.autodidacta.scheduleservice.shared.exceptions.TripAlreadyExistsException;
import com.autodidacta.scheduleservice.shared.exceptions.TripNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private static final Integer BUS_CAPACITY = 40;

    private final TripRepository tripRepository;
    private final ScheduleRepository scheduleRepository;
    private final RouteRepository routeRepository;

    @Override
    public TripResponse createTrip(TripRequest tripRequest) {
        Schedule schedule = scheduleRepository.findById(tripRequest.scheduleId()).orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));

        if (tripRepository.existsByScheduleAndDate(schedule, tripRequest.date())) {
            throw new TripAlreadyExistsException("Trip already exists for this schedule and date");
        }

        Trip trip = Trip.builder()
                .schedule(schedule)
                .date(tripRequest.date())
                .capacity(BUS_CAPACITY)
                .bookedSeats(0)
                .status(TripStatus.SCHEDULED)
                .build();

        Trip tripSaved = tripRepository.save(trip);

        Integer availableSeats = BUS_CAPACITY - trip.getBookedSeats();

        RouteResponse routeResponse = new RouteResponse(
                schedule.getRoute().getRouteId(),
                schedule.getRoute().getName(),
                schedule.getRoute().getOrigin(),
                schedule.getRoute().getDestination(),
                schedule.getRoute().getPrice(),
                schedule.getRoute().getStops()
                        .stream()
                        .map(stop -> new StopResponse(
                                stop.getStopId(), stop.getName()
                        ))
                        .toList()
        );

        ScheduleResponse scheduleResponse = new ScheduleResponse(
                schedule.getScheduleId(),
                routeResponse,
                schedule.getDepartureTime(),
                schedule.getArrivalTime()
        );

        return new TripResponse(
                tripSaved.getTripId(),
                scheduleResponse,
                tripSaved.getDate(),
                tripSaved.getCapacity(),
                availableSeats,
                tripSaved.getStatus()
        );
    }

    @Override
    public List<TripResponse> getTripForDate(LocalDate date) {
        List<Trip> trips = tripRepository.findByDate(date);

        return trips.stream()
                .map(this::toTripResponse)
//              .map(trip -> toTripResponse(trip))
                .toList();
    }

    @Override
    public TripResponse bookSeats(UUID tripId, Integer quantity) {
        Trip trip =  tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException("Trip not found"));
        trip.bookSeat(quantity);

        Trip tripSaved = tripRepository.save(trip);

        return toTripResponse(tripSaved);
    }

    private TripResponse toTripResponse(Trip trip) {
        Trip tripSaved = tripRepository.save(trip);
        return new TripResponse(
                tripSaved.getTripId(),
                new ScheduleResponse(
                        tripSaved.getSchedule().getScheduleId(),
                        new RouteResponse(
                                tripSaved.getSchedule().getRoute().getRouteId(),
                                tripSaved.getSchedule().getRoute().getName(),
                                tripSaved.getSchedule().getRoute().getOrigin(),
                                tripSaved.getSchedule().getRoute().getDestination(),
                                tripSaved.getSchedule().getRoute().getPrice(),
                                tripSaved.getSchedule().getRoute().getStops()
                                        .stream()
                                        .map(stop -> new StopResponse(stop.getStopId(), stop.getName()))
                                        .toList()
                        ),
                        tripSaved.getSchedule().getArrivalTime(),
                        tripSaved.getSchedule().getDepartureTime()
                ),
                tripSaved.getDate(),
                tripSaved.getCapacity(),
                BUS_CAPACITY - tripSaved.getBookedSeats(),
                tripSaved.getStatus()
        );
    }
}
