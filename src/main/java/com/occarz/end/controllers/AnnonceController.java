package com.occarz.end.controllers;

import java.util.ArrayList;
import java.util.List;

import com.occarz.end.dto.ResultatFiltreAnnonce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.occarz.end.dto.FiltreAnnonce;
import com.occarz.end.dto.RestResponse;
import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.repository.AnnonceRepository;
import com.occarz.end.services.annonces.AnnonceService;

@RestController
@RequestMapping("/api/v1/annonces")
public class AnnonceController {
    @Autowired
    private AnnonceService annonceService;

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
}
