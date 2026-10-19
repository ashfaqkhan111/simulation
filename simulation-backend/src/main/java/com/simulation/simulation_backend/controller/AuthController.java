package com.simulation.simulation_backend.controller;

import com.simulation.simulation_backend.dto.LoginRequest;
import com.simulation.simulation_backend.dto.LoginResponse;
import com.simulation.simulation_backend.service.JwtService;
import com.simulation.simulation_backend.service.UserService;
import com.simulation.simulation_backend.entity.User;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController{
 
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping ("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        try{
            User user = userService.authenticate(request.identifier(), request.password());

            String token = jwtService.generateToken(user.getId(), user.getUsername());

            LoginResponse response = new LoginResponse(token,"Bearer",3600L);

            return ResponseEntity.ok(response);
            
        }catch(BadCredentialsException ex){
            return ResponseEntity.status((HttpStatus.UNAUTHORIZED)).body("Invalid email/username or password");
        }
    }
        
    
}
