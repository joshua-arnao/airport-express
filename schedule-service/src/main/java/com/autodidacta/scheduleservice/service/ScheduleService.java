package com.autodidacta.scheduleservice.service;

import java.util.List;
import java.util.UUID;

import com.autodidacta.scheduleservice.dto.ScheduleRequest;
import com.autodidacta.scheduleservice.dto.ScheduleResponse;

public interface ScheduleService {
    ScheduleResponse createSchedule(ScheduleRequest scheduleRequest);

    ScheduleResponse getScheduleId(UUID scheduleId);

    List<ScheduleResponse> getAllSchedule();

    List<ScheduleResponse> getSchedulesByRouteId(UUID routeId);
}
