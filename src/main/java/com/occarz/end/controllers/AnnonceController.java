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

    // Front office (USER ou TOUT LE MONDE)
    @GetMapping("/annonces")
    public RestResponse<ResultatFiltreAnnonce> obtenirAnnonces(@RequestBody FiltreAnnonceRequete filtreAnnonce) {
        FiltreAnnonce filtre = filtreAnnonceService.build(filtreAnnonce);

        return new RestResponse<ResultatFiltreAnnonce>(new ResultatFiltreAnnonce(filtre, (ArrayList<Annonce>) annonceService.filtrerAnnonces(filtre)));
    }

    @GetMapping(value = "/annonces", params = {"ordre", "field"})
    public RestResponse<ResultatFiltreAnnonce> obtenirAnnoncesTrier(@RequestBody FiltreAnnonceRequete filtreAnnonce,
                                                                   @RequestParam("ordre") String ordre,
                                                                   @RequestParam("field") String field)  {
        FiltreAnnonce filtre = filtreAnnonceService.build(filtreAnnonce);
        return new RestResponse<ResultatFiltreAnnonce>(new ResultatFiltreAnnonce(filtre, (ArrayList<Annonce>) annonceService.trierAnnonce(filtre, ordre, field)));
    }

    // Back office (ADMIN)
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

    @GetMapping(value = "/admin/annonces", params = {"order", "field"})
    public RestResponse<ResultatFiltreAnnonce> annoncesEnAttenteDeValidationTrier(@RequestParam("ordre") String ordre,
                                                                             @RequestParam("field") String field) {
        UserDetailsImpl utilisateur = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        FiltreAnnonceRequete filtreAnnonce = new FiltreAnnonceRequete();
        filtreAnnonce.setOwner(utilisateur.getPublicUserInformation().getId());
        filtreAnnonce.setStatusAnnonce(Annonce.AnnonceState.EN_ATTENTE.ordinal());

        // Build annonce
        FiltreAnnonce filtre = filtreAnnonceService.build(filtreAnnonce);

        ResultatFiltreAnnonce resultatFiltreAnnonce = new ResultatFiltreAnnonce(filtre, (ArrayList<Annonce>) annonceService.trierAnnonce(filtre, ordre, field));

        return new RestResponse<ResultatFiltreAnnonce>(resultatFiltreAnnonce);
    }

    @GetMapping("/admin/annonces/valider/{id}")
    public RestResponse<String> validerAnnonce(@PathVariable("id") int idAnnonce) {
        annonceService.validerAnnonce(idAnnonce);

        return new RestResponse<>("Annonce maintenant disponible.");
    }
}
