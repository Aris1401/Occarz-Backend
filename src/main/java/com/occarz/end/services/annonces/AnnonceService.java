package com.occarz.end.services.annonces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.ArrayUtils;

import com.occarz.end.dto.FiltreAnnonce;
import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.annonce.SousAnnonce;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.entities.vehicule.BoiteDeVitesse;
import com.occarz.end.entities.vehicule.Carburant;
import com.occarz.end.entities.vehicule.CategorieVehicule;
import com.occarz.end.repository.AnnonceRepository;
import com.occarz.end.repository.SousAnnonceRepository;

@Service
public class AnnonceService {
    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private SousAnnonceRepository sousAnnonceRepository;

    // Utils
    void innerJoinAnnonceResults(ArrayList<Annonce> annonces, ArrayList<Annonce> resultats) {
        if (annonces.isEmpty()) annonces.addAll(resultats);
            else if (resultats.isEmpty()) annonces = resultats;
            else annonces.retainAll(resultats);
    }

    public ArrayList<Annonce> filtrerAnnonces(FiltreAnnonce filtreAnnonce) {
        // Si il ya aucun filtre
        if (filtreAnnonce == null) return (ArrayList<Annonce>) annonceRepository.findAll();
        ArrayList<Annonce> annonces = new ArrayList<>();
        
        // Filtrer par utilisateur
        if (filtreAnnonce.getUtilisateur() == null) annonces = (ArrayList<Annonce>) annonceRepository.findAll();
        else annonces = (ArrayList<Annonce>) annonceRepository.findByUtilisateur(filtreAnnonce.getUtilisateur());

        // Si le filtre specifie le proprietaire
        if (filtreAnnonce.getUtilisateur() == null && filtreAnnonce.getOwner() != null)
            annonces = (ArrayList<Annonce>) annonceRepository.findByUtilisateurNot(filtreAnnonce.getOwner());

        // FILTRES
        // Mots cle
        if (filtreAnnonce.getMotCle() != null && !filtreAnnonce.getMotCle().trim().isEmpty()) {
            ArrayList<Annonce> motCles = (ArrayList<Annonce>) annonceRepository.findByTitreOrDescriptionContaining(filtreAnnonce.getMotCle());
            
            innerJoinAnnonceResults(annonces, motCles);
        }

        // Date min et date max
        if (filtreAnnonce.getDatePublicationMinimum() != null && filtreAnnonce.getDatePublicationMaximum() != null) {
            ArrayList<Annonce> results = (ArrayList<Annonce>) annonceRepository.findByDateAnnonceBetween(filtreAnnonce.getDatePublicationMinimum(), filtreAnnonce.getDatePublicationMaximum());
            innerJoinAnnonceResults(annonces, results);
        } 
        
        if (filtreAnnonce.getDatePublicationMinimum() != null) {
            ArrayList<Annonce> results = (ArrayList<Annonce>) annonceRepository.findByDateAnnonceGreaterThanEqual(filtreAnnonce.getDatePublicationMinimum());
            innerJoinAnnonceResults(annonces, results);
        } else if (filtreAnnonce.getDatePublicationMaximum() != null) {
            ArrayList<Annonce> results = (ArrayList<Annonce>) annonceRepository.findByDateAnnonceLessThanEqual(filtreAnnonce.getDatePublicationMaximum());
            innerJoinAnnonceResults(annonces, results);
        }

        // Prix
        if (filtreAnnonce.getPrixMinimum() != -1 && filtreAnnonce.getPrixMaximum() != -1) {
            innerJoinAnnonceResults(annonces, (ArrayList<Annonce>) annonceRepository.findByPrixBetween(filtreAnnonce.getPrixMinimum(), filtreAnnonce.getPrixMaximum()));
        }

        if (filtreAnnonce.getPrixMinimum() != -1) {
            innerJoinAnnonceResults(annonces, (ArrayList<Annonce>) annonceRepository.findByPrixGreaterThanEqual(filtreAnnonce.getPrixMinimum()));
        } else if (filtreAnnonce.getPrixMaximum() != -1) {
            innerJoinAnnonceResults(annonces, (ArrayList<Annonce>) annonceRepository.findByPrixLessThanEqual(filtreAnnonce.getPrixMaximum()));
        }


        // CATEGORIES
        // Boite de vitesse
        if (filtreAnnonce.getBoiteDeVitesses() != null) {
            annonces.removeIf(annonce -> {
                boolean contains = false;

                for (BoiteDeVitesse boiteDeVitesse : filtreAnnonce.getBoiteDeVitesses()) {
                    if (annonce.getBoiteDeVitesses().contains(boiteDeVitesse)) contains = true;
                }

                return !contains;
            });
        } 

        // Carburant
        if (filtreAnnonce.getCarburants() != null) {
            annonces.removeIf(annonce -> {
                boolean contains = false;

                for (Carburant carburant : filtreAnnonce.getCarburants()) {
                    if (annonce.getCarburants().contains(carburant)) contains = true;
                }

                return !contains;
            });
        }

        // Categorie vehicule
        if (filtreAnnonce.getCategoriesVehicule() != null) {
            annonces.removeIf(annonce -> {
                boolean contains = false;

                for (CategorieVehicule categorieVehicule : filtreAnnonce.getCategoriesVehicule()) {
                    if (annonce.getCategoriesVehicule().contains(categorieVehicule)) contains = true;
                }

                return !contains;
            });
        }
        return annonces;

    }

    public ArrayList<Annonce> annoncesForUtilisateur(Utilisateur utilisateur) {
        // Creer un filtre
        FiltreAnnonce filtreAnnonce = new FiltreAnnonce();
        filtreAnnonce.setUtilisateur(utilisateur);

        return filtrerAnnonces(filtreAnnonce);
    }

    // Trier annonces
    public List<Annonce> trierAnnonce(FiltreAnnonce filtres, String ordre, String field) {
        // Annonces filtrer
        List<Annonce> annonces = filtrerAnnonces(filtres);

        switch (field) {
            case "date" -> {
                annonces.sort((annonce1, annonce2) -> {
                    switch (ordre) {
                        case "asc" -> {
                            return annonce1.getDateAnnonce().compareTo(annonce2.getDateAnnonce());
                        }
                        case "desc" -> {
                            return annonce2.getDateAnnonce().compareTo(annonce1.getDateAnnonce());
                        }
                        default -> {
                            return 0;
                        }
                    }
                });
            }

            case "prix" -> {
                annonces.sort(Comparator.comparingDouble(Annonce::getPrix));

                if (ordre.equals("desc")) {
                    Collections.reverse(annonces);
                }
            }
        }

        return annonces;
    }
}
