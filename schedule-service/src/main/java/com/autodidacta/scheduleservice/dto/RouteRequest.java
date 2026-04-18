package com.autodidacta.scheduleservice.dto;

import java.math.BigDecimal;

public record RouteRequest(
        String name,
        String origin,
        String destination,
        BigDecimal price
) {
}
