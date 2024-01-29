package com.occarz.end.controllers.vehicule;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.BoiteDeVitesse;
import com.occarz.end.entities.vehicule.Marque;
import com.occarz.end.repository.voiture.BoiteDeVitesseRepository;
import com.occarz.end.repository.voiture.MarqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/boiteDeVitesses")
@CrossOrigin(origins = "*")
public class BoiteDeVitesseController {
    @Autowired
    BoiteDeVitesseRepository boiteDeVitesseRepository;

    @GetMapping("")
    public RestResponse<List<BoiteDeVitesse>> allMarques() {
        return new RestResponse<>(boiteDeVitesseRepository.findAll());
    }

    @GetMapping("/{idMarque}")
    public RestResponse<BoiteDeVitesse> marqueById(@PathVariable("idMarque") int idMarque) {
        return new RestResponse<>(boiteDeVitesseRepository.findById(idMarque).get());
    }

    @DeleteMapping("/{idMarque}")
    public void deleteMarque(@PathVariable("idMarque") int idMarque) {
        boiteDeVitesseRepository.deleteById(idMarque);
    }

    @PutMapping("")
    public RestResponse<BoiteDeVitesse> updateMarque(@RequestBody BoiteDeVitesse marque) {
        return new RestResponse<>(boiteDeVitesseRepository.save(marque));
    }

    @PostMapping("")
    public RestResponse<BoiteDeVitesse> saveMarque(@RequestBody BoiteDeVitesse marque) {
        return new RestResponse<>(boiteDeVitesseRepository.save(marque));
    }
}
