package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.dto.LoginRequest;
import com.simulation.simulation_backend.dto.LoginResponse;
import com.simulation.simulation_backend.dto.LoginRequest;
import com.simulation.simulation_backend.entity.User;
import com.simulation.simulation_backend.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service 
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request){
        if (request.identifier() == null || request.identifier().isBlank() || request.password() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"identifier and password are required");

        }

        String identifier = request.identifier().trim();

        User user = userRepository.findByUsername(identifier).or(() -> userRepository.findByEmail(identifier)).orElseThrow(()
            -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid email or username"));

            if(!passwordEncoder.matches(request.password(), user.getPasswordHash())){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Inalid uername/email or password");
            }

            String token = jwtService.generateToken(user.getId(), user.getUsername());

            return  new LoginResponse(token, "Bearer",3600);
    }
}
