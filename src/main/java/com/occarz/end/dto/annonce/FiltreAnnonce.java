package com.occarz.end.dto.annonce;

import java.util.ArrayList;
import java.util.Date;

import com.occarz.end.entities.annonce.Annonce.AnnonceState;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.entities.vehicule.AnneeModele;
import com.occarz.end.entities.vehicule.BoiteDeVitesse;
import com.occarz.end.entities.vehicule.Carburant;
import com.occarz.end.entities.vehicule.CategorieVehicule;
import com.occarz.end.entities.vehicule.CouleurVehicule;
import com.occarz.end.entities.vehicule.EtatVehicule;
import com.occarz.end.entities.vehicule.Marque;
import com.occarz.end.entities.vehicule.Modele;
import com.occarz.end.entities.vehicule.NombrePlaces;
import com.occarz.end.entities.vehicule.Vehicule;

import lombok.Data;

@Data
public class FiltreAnnonce {
    private Utilisateur owner;
    private Utilisateur utilisateur;

    String motCle;
    Date datePublicationMinimum;
    Date datePublicationMaximum;
    double prixMinimum = -1;
    double prixMaximum = -1;

    // Categorie
    ArrayList<Vehicule> vehicules;
    ArrayList<BoiteDeVitesse> boiteDeVitesses;
    ArrayList<Carburant> carburants;
    ArrayList<CategorieVehicule> categoriesVehicule;
    ArrayList<NombrePlaces> places;
    ArrayList<CouleurVehicule> couleurVehicules;
    ArrayList<EtatVehicule> etatVehicules;

    AnnonceState statusAnnonce;

    public String getMotCle() {
        return this.motCle == null ? null : this.motCle.toLowerCase();
    }

    public ArrayList<Marque> getMarques() {
        ArrayList<Marque> marques = new ArrayList<>();

        if (vehicules != null) {
            for (Vehicule vehicule : vehicules) {
                marques.add(vehicule.getMarque());
            }
        }

        return marques;
    }

    public ArrayList<Modele> getModeles() {
        ArrayList<Modele> modeles = new ArrayList<>();

        if (vehicules != null) {
            for (Vehicule vehicule : vehicules) {
                modeles.add(vehicule.getModele());
            }
        }

        return modeles;
    }
}
