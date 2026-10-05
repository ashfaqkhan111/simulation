package com.simulation.simulation_backend.repository;

import com.simulation.simulation_backend.entity.SimulationSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SimulationSessionRepository extends JpaRepository<SimulationSession, Long>{
    List<SimulationSession> findByID(Long userId);
    
}
