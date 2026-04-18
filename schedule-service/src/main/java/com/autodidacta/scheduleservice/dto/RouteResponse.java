package com.autodidacta.scheduleservice.dto;

import com.autodidacta.scheduleservice.entity.Stop;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RouteResponse(
        UUID routeId,
        String name,
        String origin,
        String destination,
        BigDecimal price,
        List<StopResponse> stops
) {
}
