package com.occarz.end.entities.annonce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.occarz.end.dto.PublicUserInformation;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.entities.vehicule.BoiteDeVitesse;
import com.occarz.end.entities.vehicule.Carburant;
import com.occarz.end.entities.vehicule.CategorieVehicule;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Annonce implements Serializable {
    // Etats annonces
    public enum AnnonceState {
        EN_ATTENTE,
        DISPONIBLE, 
        VENDU,
        SUPPRIMER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String titre;
    String description;
    Date dateAnnonce;
    double prix;
    AnnonceState etat;
    
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    Utilisateur utilisateur;

    @JsonManagedReference
    @OneToMany(mappedBy = "annonce", fetch = FetchType.LAZY)
    List<SousAnnonce> sousAnnonces;

    // Boite de vitesse
    public ArrayList<BoiteDeVitesse> getBoiteDeVitesses() {
        ArrayList<BoiteDeVitesse> boiteDeVitesses = new ArrayList<>();

        for (SousAnnonce sousAnnonce : getSousAnnonces()) {
            boiteDeVitesses.add(sousAnnonce.getBoiteDeVitesse());
        }

        return boiteDeVitesses;
    }

    public ArrayList<Carburant> getCarburants() {
        ArrayList<Carburant> carburants = new ArrayList<>();

        for (SousAnnonce sousAnnonce : getSousAnnonces()) {
            carburants.add(sousAnnonce.getCarburant());
        }

        return carburants;
    }

    public ArrayList<CategorieVehicule> getCategoriesVehicule() {
        ArrayList<CategorieVehicule> categorieVehicules = new ArrayList<>();

        for (SousAnnonce sousAnnonce : getSousAnnonces()) {
            categorieVehicules.add(sousAnnonce.getCategorieVehicule());
        }

        return categorieVehicules;
    }

    // Informations utilisateur
    public PublicUserInformation getUtilisateur() {
        return new PublicUserInformation(utilisateur);
    }
}
