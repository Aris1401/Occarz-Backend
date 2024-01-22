package com.occarz.end.dto.requests;

import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.entities.vehicule.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class FiltreAnnonceRequete {
    private int owner = -1;
    private int utilisateur = -1;

    String motCle;
    Date datePublicationMinimum;
    Date datePublicationMaximum;
    double prixMinimum = -1;
    double prixMaximum = -1;

    // Categorie
    List<VehiculeRequete> vehicules;
    List<Integer> boiteDeVitesses;
    List<Integer> carburants;
    List<Integer> categoriesVehicule;
    List<Integer> places;
    List<Integer> couleurVehicules;
    List<Integer> etatVehicules;

    int statusAnnonce = Annonce.AnnonceState.DISPONIBLE.ordinal();
}
