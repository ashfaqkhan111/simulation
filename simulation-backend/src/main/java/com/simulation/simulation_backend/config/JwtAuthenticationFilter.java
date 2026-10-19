package com.simulation.simulation_backend.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.simulation.simulation_backend.service.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override 
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")){
                String token = header.substring(7);

                try{
                    Long userId = jwtService.extractUserId(token);

                    var authentication = UsernamePasswordAuthenticationToken.authenticated(userId.toString(), null, List.of());
                    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }catch(JwtException | IllegalArgumentException ex){
                    SecurityContextHolder.clearContext();
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                    return;
                }
            }

            filterChain.doFilter(request, response);
    }
    
}
