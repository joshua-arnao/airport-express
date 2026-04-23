package com.autodidacta.scheduleservice.service;

import com.autodidacta.scheduleservice.dto.RouteResponse;
import com.autodidacta.scheduleservice.dto.ScheduleRequest;
import com.autodidacta.scheduleservice.dto.ScheduleResponse;
import com.autodidacta.scheduleservice.dto.StopResponse;
import com.autodidacta.scheduleservice.entity.Route;
import com.autodidacta.scheduleservice.entity.Schedule;
import com.autodidacta.scheduleservice.repository.RouteRepository;
import com.autodidacta.scheduleservice.repository.ScheduleRepository;
import com.autodidacta.scheduleservice.shared.exceptions.RouteNotFoundException;
import com.autodidacta.scheduleservice.shared.exceptions.ScheduleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final RouteRepository routeRepository;

    @Override
    public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest) {
        Route route = routeRepository.findById(scheduleRequest.routeId()).orElseThrow(() -> new RouteNotFoundException("Route not found"));

        Schedule schedule = Schedule.builder()
                .route(route)
                .departureTime(scheduleRequest.departureTime())
                .arrivalTime(scheduleRequest.arrivalTime())
                .build();

        Schedule scheduleSaved = scheduleRepository.save(schedule);

        RouteResponse routeResponse = new RouteResponse(
                route.getRouteId(),
                route.getName(),
                route.getOrigin(),
                route.getDestination(),
                route.getPrice(),
                route.getStops().stream()
                        .map(stop -> new StopResponse(stop.getStopId(), stop.getName()))
                        .toList()
        );

        return new ScheduleResponse(
                scheduleSaved.getScheduleId(),
                routeResponse,
                scheduleSaved.getDepartureTime(),
                scheduleSaved.getArrivalTime()
        );
    }

    @Override
    public ScheduleResponse getScheduleId(UUID scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));
        Route route = schedule.getRoute();

        RouteResponse routeResponse = new RouteResponse(
                route.getRouteId(),
                route.getName(),
                route.getOrigin(),
                route.getDestination(),
                route.getPrice(),
                route.getStops().stream()
                        .map(stop -> new StopResponse(stop.getStopId(), stop.getName()))
                        .toList()
        );
        return new ScheduleResponse(
                schedule.getScheduleId(),
                routeResponse,
                schedule.getDepartureTime(),
                schedule.getArrivalTime()
        );
    }

    @Override
    public List<ScheduleResponse> getAllSchedule() {
        List<Schedule> schedules = scheduleRepository.findAll();

        return schedules.stream()
                .map(schedule ->

                        new ScheduleResponse(
                                schedule.getScheduleId(),
                                new RouteResponse(
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
                                ),
                                schedule.getDepartureTime(),
                                schedule.getArrivalTime()
                        ))
                .toList();
    }
}
