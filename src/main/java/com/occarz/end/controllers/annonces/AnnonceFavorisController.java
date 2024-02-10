package com.occarz.end.controllers.annonces;

import com.occarz.end.dto.annonce.FiltreAnnonce;
import com.occarz.end.dto.annonce.ResultatFiltreAnnonce;
import com.occarz.end.dto.requests.FiltreAnnonceRequete;
import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.annonce.AnnonceFavoris;
import com.occarz.end.services.annonces.AnnonceService;
import com.occarz.end.services.annonces.FiltreAnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user/annonces/favoris")
public class AnnonceFavorisController {
    @Autowired
    AnnonceService annonceService;
    @Autowired
    FiltreAnnonceService filtreAnnonceService;

    @PostMapping("/{id}")
    public RestResponse<String> ajouterAnnonceAuxFavoris(@PathVariable("id") int idAnnonce) {
        annonceService.ajouterAnnonceAuxFavoris(idAnnonce);

        return new RestResponse<>("Annonce ajouter aux favoris");
    }

    @GetMapping("")
    public RestResponse<ResultatFiltreAnnonce> listeDesFavoris(@RequestBody FiltreAnnonceRequete filtreAnnonceRequete) {
        ArrayList<AnnonceFavoris> annonceFavorises = annonceService.annoncesFavoris();

        // Obtenir les annonces en elles-memes
        List<Annonce> annonces = annonceFavorises.stream()
                .map(favoris -> {
                    Annonce annonce = favoris.getAnnonce();
                    annonce.setDateAnnonce(favoris.getDateFavoris());

                    return annonce;
                })
                .collect(Collectors.toList());

        // Filtre
        FiltreAnnonce filtreAnnonce = filtreAnnonceService.build(filtreAnnonceRequete);
        ArrayList<Annonce> annoncesFiltered = annonceService.filtrerAnnonces(filtreAnnonce);

        annoncesFiltered.retainAll(annonces);

        // Resultats filtre
        ResultatFiltreAnnonce resultatFiltreAnnonce = new ResultatFiltreAnnonce(filtreAnnonce, annoncesFiltered);
        return new RestResponse<>(resultatFiltreAnnonce);
    }

    @GetMapping(value = "", params = {"ordre", "field"})
    public RestResponse<ResultatFiltreAnnonce> listeDesFavorisTrier(@RequestBody FiltreAnnonceRequete filtreAnnonceRequete,
                                                                    @RequestParam("ordre") String ordre,
                                                                    @RequestParam("field") String field) {
        ArrayList<AnnonceFavoris> annonceFavorises = annonceService.annoncesFavoris();

        // Obtenir les annonces en elles-memes
        List<Annonce> annonces = annonceFavorises.stream()
                .map(favoris -> {
                    Annonce annonce = favoris.getAnnonce();
                    annonce.setDateAnnonce(favoris.getDateFavoris());

                    return annonce;
                })
                .collect(Collectors.toList());

        // Filtre
        FiltreAnnonce filtreAnnonce = filtreAnnonceService.build(filtreAnnonceRequete);
        ArrayList<Annonce> annoncesFiltered = annonceService.filtrerAnnonces(filtreAnnonce);

        annoncesFiltered.retainAll(annonces);

        // Resultats filtre
        ResultatFiltreAnnonce resultatFiltreAnnonce = new ResultatFiltreAnnonce(filtreAnnonce, (ArrayList<Annonce>) annonceService.trierAnnonce(annoncesFiltered, ordre, field));
        return new RestResponse<>(resultatFiltreAnnonce);
    }

    @DeleteMapping("/{id}")
    public void enleverAnnonceDesFavoris(@PathVariable("id") int idAnnonce) {
        annonceService.enleverAnnonceDesFavoris(idAnnonce);
    }

    @PostMapping("/")
    public RestResponse<ResultatFiltreAnnonce> listeDesFavorisWithData(@RequestBody FiltreAnnonceRequete filtreAnnonceRequete) {
        ArrayList<AnnonceFavoris> annonceFavorises = annonceService.annoncesFavoris();

        // Obtenir les annonces en elles-memes
        List<Annonce> annonces = annonceFavorises.stream()
                .map(favoris -> {
                    Annonce annonce = favoris.getAnnonce();
                    annonce.setDateAnnonce(favoris.getDateFavoris());

                    return annonce;
                })
                .collect(Collectors.toList());

        // Filtre
        FiltreAnnonce filtreAnnonce = filtreAnnonceService.build(filtreAnnonceRequete);
        ArrayList<Annonce> annoncesFiltered = annonceService.filtrerAnnonces(filtreAnnonce);

        annoncesFiltered.retainAll(annonces);

        // Resultats filtre
        ResultatFiltreAnnonce resultatFiltreAnnonce = new ResultatFiltreAnnonce(filtreAnnonce, annoncesFiltered);
        return new RestResponse<>(resultatFiltreAnnonce);
    }

    @PostMapping(value = "/", params = {"ordre", "field"})
    public RestResponse<ResultatFiltreAnnonce> listeDesFavorisTrierWithData(@RequestBody FiltreAnnonceRequete filtreAnnonceRequete,
                                                                    @RequestParam("ordre") String ordre,
                                                                    @RequestParam("field") String field) {
        ArrayList<AnnonceFavoris> annonceFavorises = annonceService.annoncesFavoris();

        // Obtenir les annonces en elles-memes
        List<Annonce> annonces = annonceFavorises.stream()
                .map(favoris -> {
                    Annonce annonce = favoris.getAnnonce();
                    annonce.setDateAnnonce(favoris.getDateFavoris());

                    return annonce;
                })
                .collect(Collectors.toList());

        // Filtre
        FiltreAnnonce filtreAnnonce = filtreAnnonceService.build(filtreAnnonceRequete);
        ArrayList<Annonce> annoncesFiltered = annonceService.filtrerAnnonces(filtreAnnonce);

        annoncesFiltered.retainAll(annonces);

        // Resultats filtre
        ResultatFiltreAnnonce resultatFiltreAnnonce = new ResultatFiltreAnnonce(filtreAnnonce, (ArrayList<Annonce>) annonceService.trierAnnonce(annoncesFiltered, ordre, field));
        return new RestResponse<>(resultatFiltreAnnonce);
    }
}
