package com.simulation.simulation_backend.dto;

public record LoginRequest (
    String identifier,
    String password
){}
