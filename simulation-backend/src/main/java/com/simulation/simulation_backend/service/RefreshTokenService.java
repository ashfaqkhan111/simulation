package com.simulation.simulation_backend.service;
import com.simulation.simulation_backend.entity.RefreshToken;
import com.simulation.simulation_backend.entity.User;
import com.simulation.simulation_backend.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createRefreshToken(User user, long lifetimeMs) {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        String rawToken = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setTokenHash(hashToken(rawToken));
        token.setExpiresAt(
                LocalDateTime.now().plusNanos(lifetimeMs * 1_000_000L)
        );
        token.setRevoked(false);

        refreshTokenRepository.save(token);

        return rawToken;
    }

    @Transactional
public RefreshTokenRotation rotateRefreshToken(
        String rawToken,
        long lifetimeMs) {

    if (rawToken == null || rawToken.isBlank()) {
        throw new IllegalArgumentException("Refresh token is required");
    }

    RefreshToken oldToken = refreshTokenRepository
            .findByTokenHash(hashToken(rawToken))
            .orElseThrow(() -> new IllegalArgumentException(
                    "Invalid refresh token"));

    if (oldToken.isRevoked()
            || !oldToken.getExpiresAt().isAfter(LocalDateTime.now())) {
        throw new IllegalArgumentException(
                "Refresh token is invalid or expired");
    }

    oldToken.setRevoked(true);
    refreshTokenRepository.save(oldToken);

    String newRawToken = generateRawToken();

    RefreshToken replacement = new RefreshToken();
    replacement.setUser(oldToken.getUser());
    replacement.setTokenHash(hashToken(newRawToken));
    replacement.setExpiresAt(
            LocalDateTime.now().plusNanos(lifetimeMs * 1_000_000L)
    );
    replacement.setRevoked(false);

    RefreshToken saved = refreshTokenRepository.save(replacement);

    return new RefreshTokenRotation(saved, newRawToken);
}

public Optional<RefreshToken> findValidToken(String rawToken) {
    if (rawToken == null || rawToken.isBlank()) {
        return Optional.empty();
    }

    return refreshTokenRepository
            .findByTokenHash(hashToken(rawToken))
            .filter(token -> !token.isRevoked())
            .filter(token ->
                    token.getExpiresAt().isAfter(LocalDateTime.now()));
}

    @Transactional
    public void revokeRefreshToken(String rawToken) {
        findValidToken(rawToken).ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        });
    }

    private String generateRawToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    private String hashToken(String rawToken) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    rawToken.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "SHA-256 is unavailable", e);
        }
    }
}

