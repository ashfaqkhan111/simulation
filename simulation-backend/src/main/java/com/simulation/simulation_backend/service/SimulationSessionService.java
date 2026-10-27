package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.entity.SimulationSession;
import com.simulation.simulation_backend.entity.SimulationSessionStatus;
import com.simulation.simulation_backend.entity.User;
import com.simulation.simulation_backend.exception.ResourceNotFoundException;
import com.simulation.simulation_backend.repository.SimulationSessionRepository;
import com.simulation.simulation_backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SimulationSessionService {

    private final SimulationSessionRepository simulationSessionRepository;
    private final UserRepository userRepository;

    public SimulationSessionService(
            SimulationSessionRepository simulationSessionRepository,
            UserRepository userRepository) {

        this.simulationSessionRepository = simulationSessionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SimulationSession createSession(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User " + userId + " not found"
                        )
                );

        SimulationSession session = new SimulationSession();
        session.setUser(user);
        session.setStatus(SimulationSessionStatus.ACTIVE);

        return simulationSessionRepository.save(session);
    }

    public List<SimulationSession> getAllSessions(Long userId) {
        return simulationSessionRepository.findUserById(userId);
    }

    public SimulationSession getSessionById(Long id, Long userId) {
        return simulationSessionRepository
                .findByIdAndUserId(id, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Simulation session " + id + " not found"
                        )
                );
    }

    @Transactional
    public SimulationSession stopSession(Long id, Long userId) {

        SimulationSession session = getSessionById(id, userId);

        if (session.getStatus() == SimulationSessionStatus.ACTIVE) {
            session.setStatus(SimulationSessionStatus.INACTIVE);
            session.setFinishedAt(LocalDateTime.now());
        }

        return simulationSessionRepository.save(session);
    }

    @Transactional
    public void deleteSession(Long id, Long userId) {
        SimulationSession session = getSessionById(id, userId);
        simulationSessionRepository.delete(session);
    }
}