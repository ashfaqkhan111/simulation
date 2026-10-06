package com.simulation.simulation_backend.controller;

import com.simulation.simulation_backend.entity.SimulationSession;
import com.simulation.simulation_backend.service.SimulationSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping ("/api/sessions")
public class SimulationSessionController {

    private final SimulationSessionService simulationSessionService;

    public SimulationSessionController(SimulationSessionService simulationSessionService){
        this.simulationSessionService = simulationSessionService;
    }

    @PostMapping 
    public ResponseEntity<SimulationSession> createSession(@RequestBody SimulationSession session){
        SimulationSession createdSession = simulationSessionService.createSession(session);
        return ResponseEntity.ok(createdSession);
    }
    @GetMapping 
    public ResponseEntity<List<SimulationSession>> getAllSessions(){
        return ResponseEntity.ok(simulationSessionService.getAllSessions());
    }
    @GetMapping ("/{id}")
    public ResponseEntity<SimulationSession> getSessionById(@PathVariable Long id){
        return simulationSessionService.getSessionById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping ("/{id}")
    public ResponseEntity<SimulationSession> updateSimulationSession(@PathVariable Long id, @RequestBody SimulationSession session){
        session.setId(id);
        SimulationSession updatedSession = simulationSessionService.updateSimulationSession(session);
        return ResponseEntity.ok(updatedSession);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<SimulationSession> deleteSession(@PathVariable Long id){
        simulationSessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

    
}
