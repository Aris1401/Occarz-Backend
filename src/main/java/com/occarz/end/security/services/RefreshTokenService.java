package com.occarz.end.security.services;

import com.occarz.end.repository.user.UtilisateurRepository;
import com.occarz.end.security.entities.RefreshToken;
import com.occarz.end.security.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Ref;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${bezkoder.app.jwtRefreshExpirationMs}")
    Long refreshTokenDurationMs;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(int idUtilisateur) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUtilisateur(utilisateurRepository.findById(idUtilisateur).get());
        refreshToken.setExpiration(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
    
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiration().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
        }
        
        return refreshToken;
    }
    
    @Transactional
    public int deleteByUserId(int idUtilisateur) {
        return refreshTokenRepository.deleteByUtilisateur(utilisateurRepository.findById(idUtilisateur).get());
    }
}
