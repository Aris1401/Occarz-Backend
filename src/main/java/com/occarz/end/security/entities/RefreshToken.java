package com.occarz.end.security.entities;

import com.occarz.end.entities.user.Utilisateur;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "id_utilisateur")
    Utilisateur utilisateur;

    @Column(nullable = false, unique = true)
    String token;

    @Column(nullable = false)
    Instant expiration;
}
