package com.occarz.end.controllers.vehicule;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.Carburant;
import com.occarz.end.entities.vehicule.CategorieVehicule;
import com.occarz.end.repository.voiture.CarburantRepository;
import com.occarz.end.repository.voiture.CategorieVehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/categorieVehicules")
@CrossOrigin(origins = "*")
public class CategorieVehiculeController {
    @Autowired
    CategorieVehiculeRepository categorieVehiculeRepository;

    @GetMapping("")
    public RestResponse<List<CategorieVehicule>> allMarques() {
        return new RestResponse<>(categorieVehiculeRepository.findAll());
    }

    @GetMapping("/{idMarque}")
    public RestResponse<CategorieVehicule> marqueById(@PathVariable("idMarque") int idMarque) {
        return new RestResponse<>(categorieVehiculeRepository.findById(idMarque).get());
    }

    @DeleteMapping("/{idMarque}")
    public void deleteMarque(@PathVariable("idMarque") int idMarque) {
        categorieVehiculeRepository.deleteById(idMarque);
    }

    @PutMapping("")
    public RestResponse<CategorieVehicule> updateMarque(@RequestBody CategorieVehicule marque) {
        return new RestResponse<>(categorieVehiculeRepository.save(marque));
    }

    @PostMapping("")
    public RestResponse<CategorieVehicule> saveMarque(@RequestBody CategorieVehicule marque) {
        return new RestResponse<>(categorieVehiculeRepository.save(marque));
    }
}
