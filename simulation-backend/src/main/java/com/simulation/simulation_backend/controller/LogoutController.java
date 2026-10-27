package com.simulation.simulation_backend.controller;

import com.simulation.simulation_backend.dto.RefreshTokenRequest;
import com.simulation.simulation_backend.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LogoutController {

    private final RefreshTokenService refreshTokenService;

    public LogoutController(
            RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            @RequestBody RefreshTokenRequest request) {

        refreshTokenService.revokeRefreshToken(
                request.refreshToken());

        return ResponseEntity.ok(
                Map.of("message", "Logged out successfully"));
    }
}
