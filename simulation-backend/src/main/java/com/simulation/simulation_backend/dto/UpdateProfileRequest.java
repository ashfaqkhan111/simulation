package com.simulation.simulation_backend.dto;

public record UpdateProfileRequest (
    String username,
    String email,
    String fullName
){}
