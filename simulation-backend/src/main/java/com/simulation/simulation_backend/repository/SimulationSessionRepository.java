package com.simulation.simulation_backend.repository;

import com.simulation.simulation_backend.entity.SimulationSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimulationSessionRepository extends JpaRepository<SimulationSession, Long> {
}