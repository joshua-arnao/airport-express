package com.autodidacta.scheduleservice.service;

import java.util.List;
import java.util.UUID;

import com.autodidacta.scheduleservice.dto.RouteRequest;
import com.autodidacta.scheduleservice.dto.RouteResponse;
import com.autodidacta.scheduleservice.dto.StopRequest;

public interface RouteService {
    RouteResponse createRoute(RouteRequest routeRequest);

    RouteResponse getRouteId(UUID routeId);

    List<RouteResponse> getAllRoutes();

    RouteResponse addStop(UUID routeId, StopRequest request);
}
