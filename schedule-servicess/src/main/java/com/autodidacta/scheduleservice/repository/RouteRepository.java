package com.autodidacta.scheduleservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autodidacta.scheduleservice.entity.Route;

public interface RouteRepository extends JpaRepository<Route, UUID> {
    Optional<Route> findByName(String name);
}
