package com.simulation.simulation_backend.repository;

import com.simulation.simulation_backend.entity.Interactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InteractionRepository extends JpaRepository<Interactions, Long> {
    List<Interactions> findBySimulationSessionId(Long simulationSessionId);
    
}
