package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.entity.RefreshToken;

public record RefreshTokenRotation(
    RefreshToken token,
    String rawToken
) {
}
