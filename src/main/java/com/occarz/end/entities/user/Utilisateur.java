package com.occarz.end.entities.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Utilisateur {
    public enum RoleUtilisateur {
        ADMIN,
        USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String nom;
    String prenom;
    Date dateNaissance;
    @ManyToOne
    @JoinColumn(name = "id_genre")
    Genre genre;
    String email;
    @JsonIgnore
    String motDePasse;
    String contact;
    String photoProfil;
    Date dateCreation;
    RoleUtilisateur role;
}