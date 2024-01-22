package com.occarz.end.dto.user;

import com.occarz.end.entities.user.Genre;
import com.occarz.end.entities.user.Utilisateur;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class PublicUserInformation {
    int id;
    String nom;
    String prenom;
    Date dateNaissance;
    Genre genre;
    String email;
    String contact;
    String photoProfil;
    Date dateCreation;
    Utilisateur.RoleUtilisateur role;

    public PublicUserInformation(Utilisateur utilisateur) {
        setId(utilisateur.getId());
        setContact(utilisateur.getContact());
        setNom(utilisateur.getNom());
        setPrenom(utilisateur.getPrenom());
        setDateNaissance(utilisateur.getDateNaissance());
        setDateCreation(utilisateur.getDateCreation());
        setGenre(utilisateur.getGenre());
        setEmail(utilisateur.getEmail());
        setPhotoProfil(utilisateur.getPhotoProfil());
        setRole(utilisateur.getRole());
    }
}
