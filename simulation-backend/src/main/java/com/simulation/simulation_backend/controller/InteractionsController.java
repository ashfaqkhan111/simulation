package com.simulation.simulation_backend.controller;

import com.simulation.simulation_backend.entity.Interactions;
import com.simulation.simulation_backend.service.InteractionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/interactions")
public class InteractionsController {

    private final InteractionService interactionService;

    public InteractionsController(InteractionService interactionService){
        this.interactionService = interactionService;
    }

    @PostMapping 
    public ResponseEntity<Interactions> createInteraction(@RequestBody Interactions interaction){
        Interactions createdInteraction = interactionService.createInteraction(interaction);
        return ResponseEntity.ok(createdInteraction);
    }
    @GetMapping 
    public ResponseEntity<List<Interactions>> getAllInteractions(){
        return ResponseEntity.ok(interactionService.getAllInteractions());
    }
    @GetMapping ("/{id}") 
    ResponseEntity<Interactions> getInteractionById(@PathVariable Long id){
        return interactionService.getInteractionById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping ("/session/{sessionId}")
    ResponseEntity<List<Interactions>> getInteractionBySessionId(@PathVariable Long sessionId){
        return ResponseEntity.ok(interactionService.getInteractionBySessionId(sessionId));
    }
    @PutMapping ("/{id}")
    ResponseEntity<Interactions> updateInteraction(@PathVariable Long id, @RequestBody Interactions interaction){
        interaction.setId(id);
        Interactions updatedInteraction = interactionService.updateInteraction(id, interaction);
        return ResponseEntity.ok(updatedInteraction);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<Interactions> deleteInteraction(@PathVariable Long id, @RequestBody Interactions inteaction){

        return ResponseEntity.ok(interactionService.updateInteraction(id, inteaction));

    }

    
}
