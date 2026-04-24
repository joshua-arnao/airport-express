package com.autodidacta.scheduleservice.controller;

import com.autodidacta.scheduleservice.dto.RouteRequest;
import com.autodidacta.scheduleservice.dto.RouteResponse;
import com.autodidacta.scheduleservice.service.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @PostMapping
    public RouteResponse createRoute(@Valid @RequestBody RouteRequest routeRequest) {
        return routeService.createRoute(routeRequest);
    }

    @GetMapping("/{routeId}")
    public RouteResponse getRouteId(@PathVariable UUID routeId) {
        return routeService.getRouteId(routeId);
    }

    @GetMapping("/list")
    public List<RouteResponse> getAllRoutes() {
        return routeService.getAllRoutes();
    }
}
