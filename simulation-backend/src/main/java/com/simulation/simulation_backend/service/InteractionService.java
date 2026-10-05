package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.entity.Interactions;
import com.simulation.simulation_backend.repository.InteractionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionService {

    private final InteractionRepository interactionRepository;

    public InteractionService(InteractionRepository interactionRepository){
        this.interactionRepository = interactionRepository;
    }

    public Interactions createInteraction(Interactions interaction){
        return interactionRepository.save(interaction);
    }
    
    public List<Interactions> getAllInteractions(){
        return interactionRepository.findAll();
    }

    public Optional<Interactions> getInteractionById(Long sessionId){
        return interactionRepository.findById(sessionId);
    }

    public List<Interactions> getInteractionBySessionId(Long sessionId){
        return interactionRepository.findBySimulationSessionId(sessionId);
    }

    public Interactions updateInteraction(Interactions interaction){
        return interactionRepository.save(interaction);
    }

    public void deleteIntraction(Long id){
        interactionRepository.deleteById(id);
    }
}
