package com.autodidacta.scheduleservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autodidacta.scheduleservice.entity.Stop;

public interface StopRepository extends JpaRepository<Stop, UUID> {

}
