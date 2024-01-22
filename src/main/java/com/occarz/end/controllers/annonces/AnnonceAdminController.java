package com.occarz.end.controllers.annonces;

import com.occarz.end.dto.annonce.FiltreAnnonce;
import com.occarz.end.dto.annonce.ResultatFiltreAnnonce;
import com.occarz.end.dto.requests.FiltreAnnonceRequete;
import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.security.services.UserDetailsImpl;
import com.occarz.end.services.annonces.AnnonceService;
import com.occarz.end.services.annonces.FiltreAnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/admin/annonces")
public class AnnonceAdminController {
    @Autowired
    AnnonceService annonceService;
    @Autowired
    FiltreAnnonceService filtreAnnonceService;
    @GetMapping("")
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

    @GetMapping(value = "", params = {"order", "field"})
    public RestResponse<ResultatFiltreAnnonce> annoncesEnAttenteDeValidationTrier(@RequestParam("ordre") String ordre,
                                                                                  @RequestParam("field") String field) {
        UserDetailsImpl utilisateur = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        FiltreAnnonceRequete filtreAnnonce = new FiltreAnnonceRequete();
        filtreAnnonce.setOwner(utilisateur.getPublicUserInformation().getId());
        filtreAnnonce.setStatusAnnonce(Annonce.AnnonceState.EN_ATTENTE.ordinal());

        // Build annonce
        FiltreAnnonce filtre = filtreAnnonceService.build(filtreAnnonce);

        ResultatFiltreAnnonce resultatFiltreAnnonce = new ResultatFiltreAnnonce(filtre, (ArrayList<Annonce>) annonceService.trierAnnonceParFiltre(filtre, ordre, field));

        return new RestResponse<ResultatFiltreAnnonce>(resultatFiltreAnnonce);
    }

    @PostMapping("/valider/{id}")
    public RestResponse<String> validerAnnonce(@PathVariable("id") int idAnnonce) {
        annonceService.validerAnnonce(idAnnonce);

        return new RestResponse<>("Annonce maintenant disponible.");
    }
}
