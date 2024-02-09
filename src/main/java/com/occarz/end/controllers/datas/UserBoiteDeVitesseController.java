package com.occarz.end.controllers.datas;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.BoiteDeVitesse;
import com.occarz.end.repository.voiture.BoiteDeVitesseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/boiteDeVitesses")
@CrossOrigin(origins = "*")
public class UserBoiteDeVitesseController {
    @Autowired
    BoiteDeVitesseRepository boiteDeVitesseRepository;

    @GetMapping("")
    public RestResponse<List<BoiteDeVitesse>> allMarques() {
        return new RestResponse<>(boiteDeVitesseRepository.findAll());
    }
}
