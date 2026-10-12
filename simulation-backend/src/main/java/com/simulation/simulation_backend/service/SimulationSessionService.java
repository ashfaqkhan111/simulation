package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.entity.SimulationSession;
import com.simulation.simulation_backend.repository.SimulationSessionRepository;
import com.simulation.simulation_backend.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
public class SimulationSessionService {

    private final SimulationSessionRepository simulationSessionRepository;

    public SimulationSessionService(SimulationSessionRepository simulationSessionRepository){
        this.simulationSessionRepository = simulationSessionRepository;
    }

    public SimulationSession createSession(SimulationSession session){
        return simulationSessionRepository.save(session);
    }

    public List<SimulationSession> getAllSessions(){
        return simulationSessionRepository.findAll();
    }

    public Optional<SimulationSession> getSessionById(Long id){
        return simulationSessionRepository.findById(id);
    }

    // public SimulationSession updateSimulationSession (SimulationSession session){
    //     return simulationSessionRepository.save(session);
    // }

    // public void deleteSession(Long id){
    //     simulationSessionRepository.deleteById(id);
    // }

    @Transactional 
    public SimulationSession updateSession(Long id, SimulationSession incoming){
        SimulationSession existing = simulationSessionRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Simulation session "+ id+" not found"));

        existing.setStatus(incoming.getStatus());

        return simulationSessionRepository.save(existing);
    }

    @Transactional 
    public void deleteSession(Long id){
        if(!simulationSessionRepository.existsById(id)){
            throw new ResourceNotFoundException("Simulation session "+id+" not found");
        }
        simulationSessionRepository.deleteById(id);
    }
    
}
