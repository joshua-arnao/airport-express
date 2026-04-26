package com.autodidacta.scheduleservice.controller;

import com.autodidacta.scheduleservice.dto.ScheduleRequest;
import com.autodidacta.scheduleservice.dto.ScheduleResponse;
import com.autodidacta.scheduleservice.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ScheduleResponse createSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest) {
        return scheduleService.createSchedule(scheduleRequest);
    }

    @GetMapping("/{scheduleId}")
    public ScheduleResponse getScheduleId(@PathVariable UUID scheduleId) {
        return scheduleService.getScheduleId(scheduleId);
    }

    @GetMapping("/list")
    public List<ScheduleResponse> getAllSchedule() {
        return scheduleService.getAllSchedule();
    }

    @GetMapping("/route/{routeId}")
    public List<ScheduleResponse> getSchedulesByRouteId (@PathVariable UUID routeId) {
        return scheduleService.getSchedulesByRouteId(routeId);
    }
}
