package com.occarz.end.services.annonces;

import java.util.*;
import java.util.stream.Collectors;

import com.occarz.end.dto.requests.AjoutAnnonceRequete;
import com.occarz.end.dto.requests.FiltreAnnonceRequete;
import com.occarz.end.entities.annonce.AnnonceFavoris;
import com.occarz.end.entities.annonce.AnnonceImage;
import com.occarz.end.entities.annonce.SousAnnonce;
import com.occarz.end.entities.vehicule.*;
import com.occarz.end.repository.annonce.AnnonceFavorisRepository;
import com.occarz.end.repository.annonce.AnnonceImageRepository;
import com.occarz.end.repository.user.UtilisateurRepository;
import com.occarz.end.repository.voiture.*;
import com.occarz.end.security.services.UserDetailsImpl;
import com.occarz.end.services.utlisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.occarz.end.dto.annonce.FiltreAnnonce;
import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.repository.annonce.AnnonceRepository;
import com.occarz.end.repository.annonce.SousAnnonceRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnnonceService {
    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private SousAnnonceRepository sousAnnonceRepository;

    @Autowired
    private AnnonceFavorisRepository annonceFavorisRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Mettre a jour le status d'une annonce
    public void updateAnnonceStatus(int idAnnonce, Annonce.AnnonceState state) {
        annonceRepository.updateStatusAnnonce(idAnnonce, state);
    }

    // Obtenir annonce par id
    public Annonce findById(int idAnnonce) {
        return annonceRepository.findById(idAnnonce).get();
    }

    // Utils
    void innerJoinAnnonceResults(ArrayList<Annonce> annonces, ArrayList<Annonce> resultats) {
        if (annonces.isEmpty()) annonces.addAll(resultats);
            else if (resultats.isEmpty()) annonces = resultats;
            else annonces.retainAll(resultats);
    }

    // Filtrer annonces
    public ArrayList<Annonce> filtrerAnnonces(FiltreAnnonce filtreAnnonce) {
        // Si il ya aucun filtre
        if (filtreAnnonce == null) return (ArrayList<Annonce>) annonceRepository.findAll();
        ArrayList<Annonce> annonces = (ArrayList<Annonce>) annonceRepository.findAll();
        
        // Filtrer par utilisateur
        if (filtreAnnonce.getUtilisateur() != null) annonces = (ArrayList<Annonce>) annonceRepository.findByUtilisateur(filtreAnnonce.getUtilisateur());

        // Si le filtre specifie le proprietaire
        if (filtreAnnonce.getOwner() != null) {
            annonces.retainAll((ArrayList<Annonce>) annonceRepository.findByUtilisateurNot(filtreAnnonce.getOwner()));
        }

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

        // Nombre places
        if (filtreAnnonce.getPlaces() != null) {
            annonces.removeIf(annonce -> {
                boolean contains = false;

                for (NombrePlaces places : filtreAnnonce.getPlaces()) {
                    if (annonce.getPlaces().contains(places)) contains = true;
                }

                return !contains;
            });
        }

        // Couleur vehicules
        if (filtreAnnonce.getCouleurVehicules() != null) {
            annonces.removeIf(annonce -> {
                boolean contains = false;

                for (CouleurVehicule couleurVehicule : filtreAnnonce.getCouleurVehicules()) {
                    if (annonce.getCouleursVehicule().contains(couleurVehicule)) contains = true;
                }

                return !contains;
            });
        }

        // Etat vehicules
        if (filtreAnnonce.getEtatVehicules() != null) {
            annonces.removeIf(annonce -> {
                boolean contains = false;

                for (EtatVehicule etat : filtreAnnonce.getEtatVehicules()) {
                    if (annonce.getEtatVehicules().contains(etat)) contains = true;
                }

                return !contains;
            });
        }

        // Filtre status annonces
        annonces.removeIf(annonce -> annonce.getEtat() != filtreAnnonce.getStatusAnnonce());

        return annonces;
    }

    public List<Annonce> trierAnnonceParFiltre(FiltreAnnonce filtres, String order, String field) {
        return trierAnnonce(filtrerAnnonces(filtres), order, field);
    }
    // Trier annonces
    public List<Annonce> trierAnnonce(ArrayList<Annonce> annonces, String or, String fi) {
        final String ordre = or.trim().toLowerCase();
        final String field = fi.trim().toLowerCase();

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

    // Mettre a jour une annonce
    public int validerAnnonce(int idAnnonce) {
        return annonceRepository.updateStatusAnnonce(idAnnonce, Annonce.AnnonceState.DISPONIBLE);
    }

    // Ajout annonce aux favoris
    public void ajouterAnnonceAuxFavoris(int idAnnonce) {
        UserDetailsImpl utilisateur = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Obtenir l'utilisateur
        Annonce annonce = annonceRepository.findById(idAnnonce).get();
        Utilisateur user = utilisateurRepository.findById(utilisateur.getPublicUserInformation().getId()).get();

        // Creation d'annonce favoris pour utilisateur
        AnnonceFavoris annonceFavoris = new AnnonceFavoris();
        annonceFavoris.setAnnonce(annonce);
        annonceFavoris.setUtilisateur(user);
        annonceFavoris.setDateFavoris(new Date());

        annonceFavorisRepository.save(annonceFavoris);
    }

    // Enlever annonces des favoris
    public void enleverAnnonceDesFavoris(int idAnnonce) {
        UserDetailsImpl utilisateur = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Obtenir l'utilisateur
        Annonce annonce = annonceRepository.findById(idAnnonce).get();
        Utilisateur user = utilisateurRepository.findById(utilisateur.getPublicUserInformation().getId()).get();

        // Enlever annonce des favoris
        annonceFavorisRepository.deleteByAnnonceAndUtilisateur(annonce, user);
    }

    // Liste des annonces favoris de l'utilisateur connecter
    public ArrayList<AnnonceFavoris> annoncesFavoris() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Utilisateur utilisateur = utilisateurRepository.findById(userDetails.getPublicUserInformation().getId()).get();

        // Obtenir les annonces favories
        return (ArrayList<AnnonceFavoris>) annonceFavorisRepository.findByUtilisateur(utilisateur);
    }

    // Build sous annonce
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

    @Autowired
    private AnnonceImageRepository annonceImageRepository;

    @Transactional
    public Annonce ajouterAnnonce(AjoutAnnonceRequete ajoutAnnonceRequete) {
        // Creation de l'annonce
        Annonce annonce = new Annonce();
        annonce.setEtat(Annonce.AnnonceState.EN_ATTENTE);
        annonce.setDateAnnonce(new Date());
        annonce.setTitre(ajoutAnnonceRequete.getTitre());
        annonce.setDescription(ajoutAnnonceRequete.getDescription());
        annonce.setPrix(ajoutAnnonceRequete.getPrix());
        annonce.setUtilisateur(utilisateurService.obtenirUtilisateurConnecter());

        // Sous annonce
            //        int marque;
            //        int modele;
            //        int anneeModele;
            //        int boiteDeVitesse;
            //        int couleurVehicule;
            //        int carburant;
            //        int categorieVehicule;
            //        int places;
            //        int etatVehicule;
        SousAnnonce sousAnnonce = new SousAnnonce();
        if (ajoutAnnonceRequete.getMarque() != -1) sousAnnonce.setMarque(marqueRepository.findById(ajoutAnnonceRequete.getMarque()).get());
        if (ajoutAnnonceRequete.getModele() != -1) sousAnnonce.setModele(modeleRepository.findById(ajoutAnnonceRequete.getModele()).get());
        if (ajoutAnnonceRequete.getAnneeModele() != -1) sousAnnonce.setAnneeModele(anneeModeleRepository.findById(ajoutAnnonceRequete.getAnneeModele()).orElse(null));
        if (ajoutAnnonceRequete.getBoiteDeVitesse() != -1) sousAnnonce.setBoiteDeVitesse(boiteDeVitesseRepository.findById(ajoutAnnonceRequete.getBoiteDeVitesse()).get());
        if (ajoutAnnonceRequete.getCouleurVehicule() != -1) sousAnnonce.setCouleurVehicule(couleurVehiculeRepository.findById(ajoutAnnonceRequete.getCouleurVehicule()).get());
        if (ajoutAnnonceRequete.getCarburant() != -1) sousAnnonce.setCarburant(carburantRepository.findById(ajoutAnnonceRequete.getCarburant()).get());
        if (ajoutAnnonceRequete.getCategorieVehicule() != -1) sousAnnonce.setCategorieVehicule(categorieVehiculeRepository.findById(ajoutAnnonceRequete.getCategorieVehicule()).get());
        if (ajoutAnnonceRequete.getPlaces() != -1) sousAnnonce.setPlaces(nombrePlacesRepository.findById(ajoutAnnonceRequete.getPlaces()).get());
        if (ajoutAnnonceRequete.getEtatVehicule() != -1) sousAnnonce.setEtatVehicule(etatVehiculeRepository.findById(ajoutAnnonceRequete.getEtatVehicule()).get());
        // Ajout annonce
        annonce = annonceRepository.save(annonce);

        // Ajout sous annonce
        sousAnnonce.setAnnonce(annonce);
        sousAnnonce = sousAnnonceRepository.save(sousAnnonce);

        // Sauvegarde des images
        AnnonceImage annonceImage = new AnnonceImage();
        annonceImage.setIdAnnonce(annonce.getId());
        annonceImage.setImages((ArrayList<String>) ajoutAnnonceRequete.getImages());

        annonceImageRepository.save(annonceImage);

        return annonce;
    }

    public ArrayList<String> obtenirImagesAnnonce(int idAnnonce) {
        return annonceImageRepository.findByIdAnnonce(idAnnonce);
    }
}
