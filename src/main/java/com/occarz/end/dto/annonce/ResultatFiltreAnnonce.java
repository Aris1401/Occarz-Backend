package com.occarz.end.dto.annonce;

import java.util.ArrayList;

import com.occarz.end.entities.annonce.Annonce;

import com.occarz.end.entities.vehicule.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultatFiltreAnnonce {
    FiltreAnnonce filtres;
    ArrayList<Annonce> annoncesFiltrees = new ArrayList<>();

    // Nombre de resultats
    public int getNombreResultats() {
        return annoncesFiltrees.size();
    }

    public ArrayList<String> getFiltresCourants() {
        ArrayList<String> filtres = new ArrayList<>();

//        ArrayList<Vehicule> vehicules;
//        ArrayList<BoiteDeVitesse> boiteDeVitesses;
//        ArrayList<Carburant> carburants;
//        ArrayList<CategorieVehicule> categoriesVehicule;
//        ArrayList<NombrePlaces> places;
//        ArrayList<CouleurVehicule> couleurVehicules;
//        ArrayList<EtatVehicule> etatVehicules;
        for (Vehicule vehicule : getFiltres().getVehicules()) {
            filtres.add(vehicule.getMarque().getNom());
            filtres.add(vehicule.getModele().getNom());
        }

        for (BoiteDeVitesse boiteDeVitesse : getFiltres().getBoiteDeVitesses()) {
            filtres.add(boiteDeVitesse.getNom());
        }

        for (Carburant carburant : getFiltres().getCarburants()) {
            filtres.add(carburant.getNom());
        }

        for (CategorieVehicule categorieVehicule : )

        return filtres;
    }
}
