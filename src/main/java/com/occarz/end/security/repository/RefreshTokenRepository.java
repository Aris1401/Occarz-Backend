package com.occarz.end.security.repository;

import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.security.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    
    @Modifying
    int deleteByUtilisateur(Utilisateur utilisateur);
}
