package com.occarz.end.controllers.annonces;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.occarz.end.dto.annonce.ResultatFiltreAnnonce;
import com.occarz.end.dto.requests.FiltreAnnonceRequete;
import com.occarz.end.entities.annonce.AnnonceFavoris;
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
@RequestMapping("/api/v1/annonces")
@CrossOrigin( origins = "*")
public class AnnonceController {
    @Autowired
    private AnnonceService annonceService;
    @Autowired
    private FiltreAnnonceService filtreAnnonceService;

    // Front office (USER ou TOUT LE MONDE)
    @GetMapping("")
    public RestResponse<ResultatFiltreAnnonce> obtenirAnnonces(@RequestBody FiltreAnnonceRequete filtreAnnonce) {
        FiltreAnnonce filtre = filtreAnnonceService.build(filtreAnnonce);

        return new RestResponse<ResultatFiltreAnnonce>(new ResultatFiltreAnnonce(filtre, (ArrayList<Annonce>) annonceService.filtrerAnnonces(filtre)));
    }

    @GetMapping(value = "", params = {"ordre", "field"})
    public RestResponse<ResultatFiltreAnnonce> obtenirAnnoncesTrier(@RequestBody FiltreAnnonceRequete filtreAnnonce,
                                                                   @RequestParam("ordre") String ordre,
                                                                   @RequestParam("field") String field)  {
        FiltreAnnonce filtre = filtreAnnonceService.build(filtreAnnonce);
        return new RestResponse<ResultatFiltreAnnonce>(new ResultatFiltreAnnonce(filtre, (ArrayList<Annonce>) annonceService.trierAnnonceParFiltre(filtre, ordre.toLowerCase().trim(), field.toLowerCase().trim())));
    }

    @GetMapping("/{idAnnonce}/images")
    public RestResponse<ArrayList<String>> obtenirToutImageAnnonce(@PathVariable int idAnnonce) {
        return new RestResponse<>(annonceService.obtenirImagesAnnonce(idAnnonce));
    }

}
