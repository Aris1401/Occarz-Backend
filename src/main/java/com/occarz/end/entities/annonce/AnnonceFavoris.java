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

    @ManyToOne
    @JoinColumn(name = "id_annonce")
    Annonce annonce;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    Utilisateur utilisateur;

    Date dateFavoris;
}
