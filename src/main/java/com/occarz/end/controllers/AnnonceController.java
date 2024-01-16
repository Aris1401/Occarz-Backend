package com.occarz.end.controllers;

import java.util.ArrayList;

import com.occarz.end.dto.annonce.ResultatFiltreAnnonce;
import com.occarz.end.dto.requests.FiltreAnnonceRequete;
import com.occarz.end.security.services.UserDetailsImpl;
import com.occarz.end.services.annonces.FiltreAnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.occarz.end.dto.annonce.FiltreAnnonce;
import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.services.annonces.AnnonceService;

@RestController
@RequestMapping("/api/v1")
public class AnnonceController {
    @Autowired
    private AnnonceService annonceService;
    @Autowired
    private FiltreAnnonceService filtreAnnonceService;

    @GetMapping("")    
    public RestResponse<ResultatFiltreAnnonce> obtenirAnnonces(@RequestBody FiltreAnnonce filtreAnnonce) {
        return new RestResponse<ResultatFiltreAnnonce>(new ResultatFiltreAnnonce(filtreAnnonce, (ArrayList<Annonce>) annonceService.filtrerAnnonces(filtreAnnonce)));
    }

    @GetMapping("/")
    public RestResponse<ResultatFiltreAnnonce> obtenirAnnonceTrier(@RequestBody FiltreAnnonce filtreAnnonce,
                                                                   @RequestParam("order") String ordre,
                                                                   @RequestParam("field") String field)  {
        return new RestResponse<ResultatFiltreAnnonce>(new ResultatFiltreAnnonce(filtreAnnonce, (ArrayList<Annonce>) annonceService.trierAnnonce(filtreAnnonce, ordre, field)));
    }

    // Back office
    @GetMapping("/admin/annonces")
    public RestResponse<ResultatFiltreAnnonce> annoncesEnAttenteDeValidation() {
        UserDetailsImpl utilisateur = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        FiltreAnnonceRequete filtreAnnonce = new FiltreAnnonceRequete();
        filtreAnnonce.setOwner(utilisateur.getPublicUserInformation().getId());
        filtreAnnonce.setStatusAnnonce(Annonce.AnnonceState.EN_ATTENTE.ordinal());

        // Build annonce
        FiltreAnnonce filtre = filtreAnnonceService.build(filtreAnnonce);

        ResultatFiltreAnnonce resultatFiltreAnnonce = new ResultatFiltreAnnonce(filtre, annonceService.filtrerAnnonces(filtre));

        return new RestResponse<ResultatFiltreAnnonce>(resultatFiltreAnnonce);
    }

    @GetMapping("/admin/annonces/valider/{id}")
    public RestResponse<String> validerAnnonce(@PathVariable("id") int idAnnonce) {
        annonceService.validerAnnonce(idAnnonce);

        return new RestResponse<>("Annonce maintenant disponible.");
    }
}
