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
        if (getFiltres().getVehicules() != null)
            for (Vehicule vehicule : getFiltres().getVehicules()) {
                filtres.add(vehicule.getMarque().getNom());
                filtres.add(vehicule.getModele().getNom());
            }

        if (getFiltres().getBoiteDeVitesses() != null)
            for (BoiteDeVitesse boiteDeVitesse : getFiltres().getBoiteDeVitesses()) {
                filtres.add(boiteDeVitesse.getNom());
            }

        if (getFiltres().getCarburants() != null)
            for (Carburant carburant : getFiltres().getCarburants()) {
                filtres.add(carburant.getNom());
            }

        if (getFiltres().getCategoriesVehicule() != null)
            for (CategorieVehicule categorieVehicule : getFiltres().getCategoriesVehicule()) {
                filtres.add(categorieVehicule.getNom());
            }

        if (getFiltres().getPlaces() != null)
            for (NombrePlaces nombrePlaces : getFiltres().getPlaces()) {
                filtres.add(nombrePlaces.getNombre());
            }

        if (getFiltres().getCouleurVehicules() != null)
            for (CouleurVehicule couleurVehicule : getFiltres().getCouleurVehicules()) {
                filtres.add(couleurVehicule.getCouleur());
            }

        if (getFiltres().getEtatVehicules() != null)
            for (EtatVehicule etatVehicule : getFiltres().getEtatVehicules()) {
                filtres.add(etatVehicule.getNom());
            }

        return filtres;
    }
}
