package com.occarz.end.entities.annonce;

import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.occarz.end.dto.PublicUserInformation;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.entities.vehicule.*;

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
        SUPPRIMER;

        public static AnnonceState getStateFrom(int value) {
            switch (value) {
                case 2 -> {
                    return DISPONIBLE;
                } case 3 -> {
                    return VENDU;
                } case 4 -> {
                    return SUPPRIMER;
                } default -> {
                    return EN_ATTENTE;
                }
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String titre;
    String description;
    Date dateAnnonce;
    double prix;

    @Enumerated(EnumType.ORDINAL)
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

    public ArrayList<NombrePlaces> getPlaces() {
        ArrayList<NombrePlaces> places = new ArrayList<>();

        for (SousAnnonce sousAnnonce : getSousAnnonces()) {
            places.add(sousAnnonce.getPlaces());
        }

        return places;
    }

    public ArrayList<CouleurVehicule> getCouleursVehicule() {
        ArrayList<CouleurVehicule> couleurVehicules = new ArrayList<>();

        for (SousAnnonce sousAnnonce : getSousAnnonces()) {
            couleurVehicules.add(sousAnnonce.getCouleurVehicule());
        }

        return couleurVehicules;
    }

    public ArrayList<EtatVehicule> getEtatVehicules() {
        ArrayList<EtatVehicule> etatVehicules = new ArrayList<>();

        for (SousAnnonce sousAnnonce : getSousAnnonces()) {
            etatVehicules.add(sousAnnonce.getEtatVehicule());
        }

        return etatVehicules;
    }

    // Informations utilisateur
    public PublicUserInformation getUtilisateur() {
        return new PublicUserInformation(utilisateur);
    }
}
