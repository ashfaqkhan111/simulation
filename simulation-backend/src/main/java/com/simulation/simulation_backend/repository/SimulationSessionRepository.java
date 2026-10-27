package com.simulation.simulation_backend.repository;

import com.simulation.simulation_backend.entity.SimulationSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SimulationSessionRepository extends JpaRepository<SimulationSession, Long> {

    List<SimulationSession> findUserById(Long userId);

    Optional<SimulationSession> findByIdAndUserId(Long id, Long userId);
    
}