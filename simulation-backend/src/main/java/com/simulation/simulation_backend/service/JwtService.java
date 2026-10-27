package com.simulation.simulation_backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service 
public class JwtService {
    
    private final SecretKey key;
    private final long expirationMs;

    public JwtService(@Value ("${app.jwt.secret}")String secret, @Value ("${app.jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expirationMs = expirationMs;
    }

    public String generateToken(Long userId, String username){
        Instant now = Instant.now();

        return Jwts.builder().subject(userId.toString()).claim("username", username)
        .issuedAt(Date.from(now)).expiration(Date.from(now.plusMillis(expirationMs)))
        .signWith(key).compact();
    }

    public  Long extractUserId(String token){
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        return Long.parseLong(claims.getSubject());
    }

    public boolean isValid(String token){
        try{
            extractUserId(token);
            return true;
        }catch(Exception ex){
            return false;
        }
    }
}
