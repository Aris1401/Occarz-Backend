package com.occarz.end.security.payload.request;

import com.occarz.end.entities.user.Genre;
import com.occarz.end.entities.user.Utilisateur;
import lombok.Data;

import java.util.Date;

@Data
public class InscriptionRequete {
    int id;
    String nom;
    String prenom;
    Date dateNaissance;
    String motDePasse;
    int genre;
    String email;
    String contact;
    String photoProfil;
    Date dateCreation = new Date();
}
