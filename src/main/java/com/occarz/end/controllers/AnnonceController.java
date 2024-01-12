package com.occarz.end.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public RestResponse<List<Annonce>> obtenirAnnonces(@RequestBody FiltreAnnonce filtreAnnonce) {
        return new RestResponse<List<Annonce>>(annonceService.filtrerAnnonces(filtreAnnonce));
    }
}
