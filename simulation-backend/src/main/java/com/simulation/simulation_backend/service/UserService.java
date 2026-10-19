package com.simulation.simulation_backend.service;

import com.simulation.simulation_backend.entity.User;
import com.simulation.simulation_backend.dto.*;
import com.simulation.simulation_backend.repository.UserRepository;
import com.simulation.simulation_backend.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.authentication.BadCredentialsException;


import java.util.List;
import java.util.Optional;

@Service 
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserResponse toResponse(User user){
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    private void validatePassword(String password){
        if(password == null || password.length() < 12){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password must contain at least 12 characters");
        }
    }

    private void validateProfile(String username, String email){
        if(username == null || username.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }
        if(email == null || email.isBlank() || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST," A valid email is required");
        }
    }

    @Transactional 
    public UserResponse createUser(RegisterUserRequest request){
        validateProfile(request.username(), request.email());
        validatePassword(request.password());

        if(userRepository.existsByUsername(request.username())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exist");
        }

        if(userRepository.existsByEmail(request.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email already is exsists");
        }

        User user = new User();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFullName(request.fullName());

        user.setPasswordHash(passwordEncoder.encode(request.password()));

        User savedUSer = userRepository.save(user);

        return toResponse(savedUSer);
    }

    @Transactional (readOnly = true)
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional (readOnly = true)
    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("User "+id+" not found"));

        return toResponse(user);
    }

    @Transactional 
    public UserResponse updateProfile(Long id, UpdateProfileRequest request){
        User user = userRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("User "+id+" not found"));

        validateProfile(request.username(), request.email());

        if(!user.getUsername().equals(request.username()) && userRepository.existsByUsername(request.username())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exists");
        }

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFullName(request.fullName());

        return toResponse(userRepository.save(user));
    }

    @Transactional
    public void changePassword(Long id, ChangePasswordRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> 
        new ResourceNotFoundException("User "+id+" not found"));
        
        if (request.currentPassword() == null || !passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Current password is incorrect ");
        }

        validatePassword(request.newPassword());

        if (passwordEncoder.matches(request.newPassword(), user.getPasswordHash())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must differ from the current password");
        }

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);


    }

    @Transactional (readOnly = true)
    public boolean verifyPassword(Long id, String rawPassword){
        User user = userRepository.findById(id).orElseThrow(() -> 
        new ResourceNotFoundException("user "+id+" not found"));

        return rawPassword != null && passwordEncoder.matches(rawPassword, user.getPasswordHash());
    }

    @Transactional 
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("user "+id+" not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional (readOnly = true)
    public User authenticate(String identifier, String rawPassword){
        User user = userRepository.findByUsername(identifier).or(() -> 
    userRepository.findByEmail(identifier)).orElseThrow(()-> new BadCredentialsException("Invalid credentials"));

    if(rawPassword == null || !passwordEncoder.matches(rawPassword, user.getPasswordHash())){
        throw new BadCredentialsException("Invalid credentials");
    }

    return user;
    }
    
    
}
