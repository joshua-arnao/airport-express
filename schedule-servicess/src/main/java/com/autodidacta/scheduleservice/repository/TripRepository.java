package com.autodidacta.scheduleservice.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autodidacta.scheduleservice.entity.Schedule;
import com.autodidacta.scheduleservice.entity.Trip;
import com.autodidacta.scheduleservice.entity.TripStatus;

public interface TripRepository extends JpaRepository<Trip, UUID> {
    List<Trip> findByDate(LocalDate date);

    List<Trip> findByDateAndStatus(LocalDate date, TripStatus status);

    boolean existsByScheduleAndDate(Schedule schedule, LocalDate date);
}
