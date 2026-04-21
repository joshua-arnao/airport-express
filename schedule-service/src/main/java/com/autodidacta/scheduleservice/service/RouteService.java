package com.autodidacta.scheduleservice.service;

import java.util.List;
import java.util.UUID;

import com.autodidacta.scheduleservice.dto.RouteRequest;
import com.autodidacta.scheduleservice.dto.RouteResponse;

public interface RouteService {
    RouteResponse createRoute(RouteRequest routeRequest);

    RouteResponse getRouteId(UUID routeId);

    List<RouteResponse> getAllRoutes();
}
