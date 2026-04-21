package com.autodidacta.scheduleservice.service;

import java.util.List;
import java.util.UUID;

import com.autodidacta.scheduleservice.dto.StopResponse;

public interface StopService {
    StopResponse createStop(String name, UUID routeId);

    List<StopResponse> getStopsByRouteId(UUID routeId);
}
