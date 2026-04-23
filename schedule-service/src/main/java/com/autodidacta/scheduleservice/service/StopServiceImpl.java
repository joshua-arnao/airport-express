package com.autodidacta.scheduleservice.service;

import com.autodidacta.scheduleservice.dto.StopResponse;
import com.autodidacta.scheduleservice.repository.StopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StopServiceImpl implements StopService {
    private final StopRepository stopRepository;

    @Override
    public StopResponse createStop(String name, UUID routeId) {
        return null;
    }

    @Override
    public List<StopResponse> getStopsByRouteId(UUID routeId) {
        return List.of();
    }
}
