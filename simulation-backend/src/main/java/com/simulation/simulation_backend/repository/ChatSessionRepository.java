package com.simulation.simulation_backend.repository;

import com.simulation.simulation_backend.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {

    List<ChatSession> findByUserId(Long userId);
    List<ChatSession> findBySimulationSessionId(Long simulationsessionId);

    Optional<ChatSession> findByIdAndUserId(Long id, Long userId);

}
