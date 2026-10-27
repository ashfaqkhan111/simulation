package com.simulation.simulation_backend.controller;

import com.simulation.simulation_backend.config.JwtProperties;
import com.simulation.simulation_backend.dto.LoginRequest;
import com.simulation.simulation_backend.dto.LoginResponse;
import com.simulation.simulation_backend.dto.RefreshTokenRequest;
import com.simulation.simulation_backend.dto.RefreshTokenResponse;
import com.simulation.simulation_backend.entity.User;
import com.simulation.simulation_backend.service.JwtService;
import com.simulation.simulation_backend.service.RefreshTokenRotation;
import com.simulation.simulation_backend.service.RefreshTokenService;
import com.simulation.simulation_backend.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController{
 
    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;

    public AuthController(UserService userService, JwtService jwtService, RefreshTokenService refreshTokenService, JwtProperties jwtProperties){
        this.userService = userService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.jwtProperties = jwtProperties;
    }

    @PostMapping ("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try{
            User user = userService.authenticate(request.identifier(), request.password());

            String accessToken = jwtService.generateToken(user.getId(), user.getUsername());

            String refreshToken = refreshTokenService.createRefreshToken(user, jwtProperties.getRefreshTokenExpirationMs());

            LoginResponse response = new LoginResponse(accessToken, refreshToken, "Bearer", jwtProperties.getAccessTokenExpirationMs()/1000);

            return ResponseEntity.ok(response);
        }catch(BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Invalid username/email or password"));
        }
    }

    @PostMapping ("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request){
        try {
            RefreshTokenRotation rotation = refreshTokenService.rotateRefreshToken(request.refreshToken(), jwtProperties.getAccessTokenExpirationMs());
            User user = rotation.token().getUser();

            String newAccessToken = jwtService.generateToken(user.getId(), user.getUsername());

            RefreshTokenResponse response = new RefreshTokenResponse(newAccessToken, rotation.rawToken(),"Bearer",jwtProperties.getAccessTokenExpirationMs() / 1000);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid or expired refresh token"));
        }
    }
        
    
}
