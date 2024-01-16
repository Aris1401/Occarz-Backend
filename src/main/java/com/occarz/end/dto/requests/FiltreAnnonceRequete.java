package com.occarz.end.dto.requests;

import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.entities.vehicule.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

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
    ArrayList<VehiculeRequete> vehicules;
    ArrayList<Integer> boiteDeVitesses;
    ArrayList<Integer> carburants;
    ArrayList<Integer> categoriesVehicule;
    ArrayList<Integer> places;
    ArrayList<Integer> couleurVehicules;
    ArrayList<Integer> etatVehicules;

    int statusAnnonce = -1;
}
