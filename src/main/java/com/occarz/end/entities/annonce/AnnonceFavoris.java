package com.occarz.end.entities.annonce;

import com.occarz.end.entities.user.Utilisateur;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class AnnonceFavoris {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToMany
    @JoinColumn(name = "id_annonce")
    Annonce annonce;

    @OneToMany
    @JoinColumn(name = "id_utilisateur")
    Utilisateur utilisateur;

    Date dateFavoris;
}
