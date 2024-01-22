package com.occarz.end.services.annonces;

import com.occarz.end.dto.annonce.FiltreAnnonce;
import com.occarz.end.dto.requests.FiltreAnnonceRequete;
import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.entities.vehicule.*;
import com.occarz.end.repository.user.UtilisateurRepository;
import com.occarz.end.repository.voiture.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FiltreAnnonceService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MarqueRepository marqueRepository;
    @Autowired
    private ModeleRepository modeleRepository;
    @Autowired
    private AnneeModeleRepository anneeModeleRepository;
    @Autowired
    private BoiteDeVitesseRepository boiteDeVitesseRepository;
    @Autowired
    private CarburantRepository carburantRepository;
    @Autowired
    private CategorieVehiculeRepository categorieVehiculeRepository;
    @Autowired
    private NombrePlacesRepository nombrePlacesRepository;
    @Autowired
    private CouleurVehiculeRepository couleurVehiculeRepository;
    @Autowired
    private EtatVehiculeRepository etatVehiculeRepository;

    public FiltreAnnonce build(FiltreAnnonceRequete requete) {
        FiltreAnnonce filtreAnnonce = new FiltreAnnonce();

        // Construction annonce
//        Utilisateur owner;
//        Utilisateur utilisateur;
//
//        String motCle;
//        Date datePublicationMinimum;
//        Date datePublicationMaxif (requete.getMotCle() != null && !requete.getMotCle().isEmpty()) imum;
//        double prixMinimum = -1;
//        double prixMaximum = -1;
//
//        // Categorie
//        ArrayList<Vehicule> vehicules;
//        ArrayList<BoiteDeVitesse> boiteDeVitesses;
//        ArrayList<Carburant> carburants;
//        ArrayList<Categorie*Vehicule> categoriesVehicule;
//        ArrayList<NombrePlaces> places;
//        ArrayList<CouleurVehicule> couleurVehicules;
//        ArrayList<EtatVehicule> etatVehicules;
//
//        Annonce.AnnonceState statusAnnonce;

        if (requete.getOwner() != -1) filtreAnnonce.setOwner(utilisateurRepository.findById(requete.getOwner()).get());
        if (requete.getUtilisateur() != -1)filtreAnnonce.setUtilisateur(utilisateurRepository.findById(requete.getUtilisateur()).get());

        filtreAnnonce.setMotCle(requete.getMotCle());
        filtreAnnonce.setDatePublicationMaximum(requete.getDatePublicationMaximum());
        filtreAnnonce.setDatePublicationMinimum(requete.getDatePublicationMinimum());
        filtreAnnonce.setPrixMinimum(requete.getPrixMinimum());
        filtreAnnonce.setPrixMaximum(requete.getPrixMaximum());

        // Categorie
        if (requete.getVehicules() != null) filtreAnnonce.setVehicules((ArrayList<Vehicule>) requete.getVehicules().stream().map(vehicule -> {
            Vehicule v = new Vehicule();
            v.setMarque(marqueRepository.findById(vehicule.getMarque()).get());
            v.setModele(modeleRepository.findById(vehicule.getModele()).get());
            if (vehicule.getAnneeModele() != -1)
                v.setAnneeModele(anneeModeleRepository.findById(vehicule.getAnneeModele()).get());

            return v;
        }).collect(Collectors.toList()));

        if (requete.getBoiteDeVitesses() != null) filtreAnnonce.setBoiteDeVitesses((ArrayList<BoiteDeVitesse>) requete.getBoiteDeVitesses().stream().map(boiteDeVitesse -> {
            return boiteDeVitesseRepository.findById(boiteDeVitesse).get();
        }).collect(Collectors.toList()));

        if (requete.getCarburants() != null) filtreAnnonce.setCarburants((ArrayList<Carburant>) requete.getCarburants().stream().map(carburant -> {
            return carburantRepository.findById(carburant).get();
        }).collect(Collectors.toList()));

        if (requete.getCategoriesVehicule() != null) filtreAnnonce.setCategoriesVehicule((ArrayList<CategorieVehicule>) requete.getCategoriesVehicule().stream().map(categorie -> {
            return categorieVehiculeRepository.findById(categorie).get();
        }).collect(Collectors.toList()));

        if (requete.getPlaces() != null) filtreAnnonce.setPlaces((ArrayList<NombrePlaces>) requete.getPlaces().stream().map(place -> {
            return nombrePlacesRepository.findById(place).get();
        }).collect(Collectors.toList()));

        if (requete.getCouleurVehicules() != null) filtreAnnonce.setCouleurVehicules((ArrayList<CouleurVehicule>) requete.getCouleurVehicules().stream().map(couleur -> {
            return couleurVehiculeRepository.findById(couleur).get();
        }).collect(Collectors.toList()));

        if (requete.getEtatVehicules() != null) filtreAnnonce.setEtatVehicules((ArrayList<EtatVehicule>) requete.getEtatVehicules().stream().map(etat -> {
            return etatVehiculeRepository.findById(etat).get();
        }).collect(Collectors.toList()));

        filtreAnnonce.setStatusAnnonce(Annonce.AnnonceState.fromInt(requete.getStatusAnnonce()));

        return filtreAnnonce;
    }
}
