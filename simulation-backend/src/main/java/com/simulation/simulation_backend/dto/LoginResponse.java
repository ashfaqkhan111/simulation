package com.simulation.simulation_backend.dto;

public record LoginResponse(
  String accessToken,
  String refreshToken,
  String tokenType,
  Long expiresIn   
){}
