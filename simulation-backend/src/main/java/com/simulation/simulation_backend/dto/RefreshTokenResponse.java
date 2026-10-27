package com.simulation.simulation_backend.dto;

public record RefreshTokenResponse (
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn
){
    
}
