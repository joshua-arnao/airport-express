package com.autodidacta.scheduleservice.service;

import com.autodidacta.scheduleservice.dto.RouteRequest;
import com.autodidacta.scheduleservice.dto.RouteResponse;
import com.autodidacta.scheduleservice.dto.StopResponse;
import com.autodidacta.scheduleservice.entity.Route;
import com.autodidacta.scheduleservice.repository.RouteRepository;
import com.autodidacta.scheduleservice.shared.exceptions.RouteNameAlreadyExistsException;
import com.autodidacta.scheduleservice.shared.exceptions.RouteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{
    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;

    @Override
    public RouteResponse createRoute(RouteRequest routeRequest) {
        routeRepository.findByName(routeRequest.name())
                .ifPresent(route -> {
                            throw new RouteNameAlreadyExistsException("Route name is in use");
                });

        Route route = Route.builder()
                .name(routeRequest.name())
                .origin(routeRequest.origin())
                .destination(routeRequest.destination())
                .price(routeRequest.price())
                .build();

        Route routeSaved = routeRepository.save(route);

        return new RouteResponse(
                routeSaved.getRouteId(),
                routeSaved.getName(),
                routeSaved.getOrigin(),
                routeSaved.getDestination(),
                routeSaved.getPrice(),
                List.of()
        );
    }

    @Override
    public RouteResponse getRouteId(UUID routeId) {
        Route route = routeRepository.findById(routeId).orElseThrow(()->new RouteNotFoundException("Route not found"));

        return new RouteResponse(
                route.getRouteId(),
                route.getName(),
                route.getOrigin(),
                route.getDestination(),
                route.getPrice(),
                route.getStops().stream()
                        .map(stop -> new StopResponse(stop.getStopId(), stop.getName()))
                        .toList()
        );
    }

    @Override
    public List<RouteResponse> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();

        return routes.stream()
                .map(route ->
                        new RouteResponse(
                            route.getRouteId(),
                            route.getName(),
                            route.getOrigin(),
                            route.getDestination(),
                            route.getPrice(),
                            route.getStops().stream()
                                    .map(stop -> new StopResponse(stop.getStopId(), stop.getName()))
                                    .toList()
                        ))
                .toList();
    }
}
