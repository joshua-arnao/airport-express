package com.autodidacta.scheduleservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autodidacta.scheduleservice.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    List<Schedule> findByRoute_RouteId(UUID routeId);
}
