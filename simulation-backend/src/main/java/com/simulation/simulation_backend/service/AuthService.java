package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.config.JwtProperties;
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
    private  final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;
    

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RefreshTokenService refreshTokenService, JwtProperties jwtProperties){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.jwtProperties = jwtProperties;
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

            String accessToken = jwtService.generateToken(user.getId(), user.getUsername());

            String refreshToken = refreshTokenService.createRefreshToken(user, jwtProperties.getRefreshTokenExpirationMs());

            return  new LoginResponse(accessToken,refreshToken, "Bearer",jwtProperties.getAccessTokenExpirationMs()/1000);
    }
}
