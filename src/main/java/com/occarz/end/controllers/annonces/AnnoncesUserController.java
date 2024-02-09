package com.occarz.end.controllers.annonces;

import com.occarz.end.dto.annonce.FiltreAnnonce;
import com.occarz.end.dto.annonce.ResultatFiltreAnnonce;
import com.occarz.end.dto.requests.AjoutAnnonceRequete;
import com.occarz.end.dto.requests.FiltreAnnonceRequete;
import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.repository.user.UtilisateurRepository;
import com.occarz.end.security.services.UserDetailsImpl;
import com.occarz.end.services.annonces.AnnonceService;
import com.occarz.end.services.annonces.CommissionService;
import com.occarz.end.services.annonces.FiltreAnnonceService;
import com.occarz.end.services.utlisateur.RevenuUtilisateurService;
import com.occarz.end.services.utlisateur.UtilisateurService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user/annonces")
@CrossOrigin(origins = "*")
public class AnnoncesUserController {
    @Autowired
    AnnonceService annonceService;
    @Autowired
    FiltreAnnonceService filtreAnnonceService;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    CommissionService commissionService;
    @Autowired
    RevenuUtilisateurService revenuUtilisateurService;

    @GetMapping("")
    public RestResponse<ResultatFiltreAnnonce> mesAnnonces(@RequestBody FiltreAnnonceRequete filtres) {
        // Produire le filtre des annonces
        FiltreAnnonce filtreAnnonce = filtreAnnonceService.build(filtres);

        // Ajouter l'utilsateur
        filtreAnnonce.setUtilisateur(utilisateurService.obtenirUtilisateurConnecter());

        // Resutlats
        ResultatFiltreAnnonce resultatFiltreAnnonce = new ResultatFiltreAnnonce(filtreAnnonce, annonceService.filtrerAnnonces(filtreAnnonce));
        return new RestResponse<>(resultatFiltreAnnonce);
    }

    @PostMapping("/")
    public RestResponse<ResultatFiltreAnnonce> mesAnnoncesWithData(@RequestBody FiltreAnnonceRequete filtres) {
        // Produire le filtre des annonces
        FiltreAnnonce filtreAnnonce = filtreAnnonceService.build(filtres);

        // Ajouter l'utilsateur
        filtreAnnonce.setUtilisateur(utilisateurService.obtenirUtilisateurConnecter());

        // Resutlats
        ResultatFiltreAnnonce resultatFiltreAnnonce = new ResultatFiltreAnnonce(filtreAnnonce, annonceService.filtrerAnnonces(filtreAnnonce));
        return new RestResponse<>(resultatFiltreAnnonce);
    }

    @GetMapping("/attentes")
    public RestResponse<ResultatFiltreAnnonce> mesAnnoncesEnAttente(@RequestBody FiltreAnnonceRequete filtres) {
        // Produire le filtre des annonces
        FiltreAnnonce filtreAnnonce = filtreAnnonceService.build(filtres);
        filtreAnnonce.setStatusAnnonce(Annonce.AnnonceState.EN_ATTENTE);

        // Ajouter l'utilsateur
        filtreAnnonce.setUtilisateur(utilisateurService.obtenirUtilisateurConnecter());

        // Resutlats
        ResultatFiltreAnnonce resultatFiltreAnnonce = new ResultatFiltreAnnonce(filtreAnnonce, annonceService.filtrerAnnonces(filtreAnnonce));
        return new RestResponse<>(resultatFiltreAnnonce);
    }

    @GetMapping(value = "", params = {"ordre", "field"})
    public RestResponse<ResultatFiltreAnnonce> mesAnnoncesTrier(@RequestBody FiltreAnnonceRequete filtres,
                                                                @RequestParam("order") String ordre,
                                                                @RequestParam("field") String field) {
        FiltreAnnonce filtreAnnonce = filtreAnnonceService.build(filtres);

        // Ajouter utilisateur
        filtreAnnonce.setUtilisateur(utilisateurService.obtenirUtilisateurConnecter());

        // Resultat
        ResultatFiltreAnnonce resultatFiltreAnnonce = new ResultatFiltreAnnonce(filtreAnnonce, (ArrayList<Annonce>) annonceService.trierAnnonceParFiltre (filtreAnnonce, ordre, field));
        return new RestResponse<>(resultatFiltreAnnonce);
    }

    // Marquer une annonces comme vendue
    @PostMapping("/{id}/vendu")
    public RestResponse<String> marquerAnnonceVendue(@PathVariable("id") int idAnnonce) {
        // Obtenir l'annonce voulu
        Annonce annonce = annonceService.findById(idAnnonce);

        // Checker si l'annonce n'est pas a l'utilisateur
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurConnecter();
        if (utilisateur.getId() != annonce.getUtilisateur().getId()) {
            RestResponse<String> response = new RestResponse<>("Action impossible.");
            response.setErrorMessage("Action impossible.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return response;
        }

        // Modifer l'annonce
        annonceService.updateAnnonceStatus(annonce.getId(), Annonce.AnnonceState.VENDU);

        // Ajouter a la commission du site et de l'utilisateur
        double commission = commissionService.ajouterCommissionDepuisAnnonce(annonce);
        revenuUtilisateurService.genererRevenueUtilisateurDepuisCommission(annonce, commission);

        return new RestResponse<>("Commission recuperer par le site: " + commission + "%.");
    }

    // Obtenir les annonces en cours
    @GetMapping("/attente")
    public RestResponse<ArrayList<Annonce>> annoncesEnCours() {
        // Obtenir l'utilisateur connecter
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurConnecter();

        // Creer un filtre
        FiltreAnnonce filtreAnnonce = new FiltreAnnonce();
        filtreAnnonce.setStatusAnnonce(Annonce.AnnonceState.EN_ATTENTE);
        filtreAnnonce.setUtilisateur(utilisateur);

        // Creer une reponse
        ArrayList<Annonce> annonces = annonceService.filtrerAnnonces(filtreAnnonce);
        return new RestResponse<>(annonces);
    }

    @GetMapping(value = "/attente", params = {"ordre", "field"})
    public RestResponse<ArrayList<Annonce>> annoncesEnCours(@RequestParam("ordre") String ordre,
                                                            @RequestParam("field") String field) {
        // Obtenir l'utilisateur connecter
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurConnecter();

        // Creer un filtre
        FiltreAnnonce filtreAnnonce = new FiltreAnnonce();
        filtreAnnonce.setStatusAnnonce(Annonce.AnnonceState.EN_ATTENTE);
        filtreAnnonce.setUtilisateur(utilisateur);

        // Creer une reponse
        ArrayList<Annonce> annonces = annonceService.filtrerAnnonces(filtreAnnonce);
        annonces = (ArrayList<Annonce>) annonceService.trierAnnonce(annonces, ordre, field);
        return new RestResponse<>(annonces);
    }

    @PostMapping("")
    public RestResponse<Annonce> ajouterAnnonce(@RequestBody AjoutAnnonceRequete ajoutAnnonceRequete) {
        return new RestResponse<>(annonceService.ajouterAnnonce(ajoutAnnonceRequete));
    }
}
