package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.entity.Interactions;
import com.simulation.simulation_backend.repository.InteractionRepository;
import com.simulation.simulation_backend.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // public Interactions updateInteraction(Interactions interaction){
    //     return interactionRepository.save(interaction);
    // }

    // public void deleteIntraction(Long id){
    //     interactionRepository.deleteById(id);
    // }

    @Transactional
    public Interactions updateInteraction(Long id, Interactions incoming){
        Interactions existing = interactionRepository.findById(id).orElseThrow(() -> 
        new ResourceNotFoundException("interacton "+id+" not found"));

        existing.setAction(incoming.getAction());
        existing.setObjectName(incoming.getObjectName());
        existing.setTemprature(incoming.getTemprature());
        existing.setData(incoming.getData());

        return interactionRepository.save(existing);
    }
    @Transactional 
    public void deleteInteraction(Long id){
        if(!interactionRepository.existsById(id)){
            throw new ResourceNotFoundException("interaction "+id+ " not found");
        }
    }
}
