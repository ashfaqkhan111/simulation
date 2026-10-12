package com.simulation.simulation_backend.dto;

public record ChangePasswordRequest (
    String currentPassword,
    String newPassword
){}
