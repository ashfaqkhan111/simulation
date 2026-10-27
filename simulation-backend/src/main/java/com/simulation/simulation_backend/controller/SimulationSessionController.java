package com.simulation.simulation_backend.controller;

import com.simulation.simulation_backend.entity.SimulationSession;
import com.simulation.simulation_backend.service.SimulationSessionService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SimulationSessionController {

    private final SimulationSessionService simulationSessionService;

    public SimulationSessionController(
            SimulationSessionService simulationSessionService) {
        this.simulationSessionService = simulationSessionService;
    }

    private Long getAuthenticatedUserId(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }

    @PostMapping
    public ResponseEntity<SimulationSession> createSession(
            Authentication authentication) {

        Long userId = getAuthenticatedUserId(authentication);

        SimulationSession createdSession =
                simulationSessionService.createSession(userId);

        return ResponseEntity.ok(createdSession);
    }

    @GetMapping
    public ResponseEntity<List<SimulationSession>> getAllSessions(
            Authentication authentication) {

        Long userId = getAuthenticatedUserId(authentication);

        return ResponseEntity.ok(
                simulationSessionService.getAllSessions(userId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulationSession> getSessionById(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getAuthenticatedUserId(authentication);

        return ResponseEntity.ok(
                simulationSessionService.getSessionById(id, userId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SimulationSession> stopSession(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getAuthenticatedUserId(authentication);

        return ResponseEntity.ok(
                simulationSessionService.stopSession(id, userId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getAuthenticatedUserId(authentication);

        simulationSessionService.deleteSession(id, userId);

        return ResponseEntity.noContent().build();
    }
}