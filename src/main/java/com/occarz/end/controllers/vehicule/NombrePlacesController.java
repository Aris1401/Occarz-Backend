package com.occarz.end.controllers.vehicule;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.CategorieVehicule;
import com.occarz.end.entities.vehicule.NombrePlaces;
import com.occarz.end.repository.voiture.CategorieVehiculeRepository;
import com.occarz.end.repository.voiture.NombrePlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/nombrePlaces")
@CrossOrigin(origins = "*")
public class NombrePlacesController {
    @Autowired
    NombrePlacesRepository nombrePlacesRepository;

    @GetMapping("")
    public RestResponse<List<NombrePlaces>> allMarques() {
        return new RestResponse<>(nombrePlacesRepository.findAll());
    }

    @GetMapping("/{idMarque}")
    public RestResponse<NombrePlaces> marqueById(@PathVariable("idMarque") int idMarque) {
        return new RestResponse<>(nombrePlacesRepository.findById(idMarque).get());
    }

    @DeleteMapping("/{idMarque}")
    public void deleteMarque(@PathVariable("idMarque") int idMarque) {
        nombrePlacesRepository.deleteById(idMarque);
    }

    @PutMapping("")
    public RestResponse<NombrePlaces> updateMarque(@RequestBody NombrePlaces marque) {
        return new RestResponse<>(nombrePlacesRepository.save(marque));
    }

    @PostMapping("")
    public RestResponse<NombrePlaces> saveMarque(@RequestBody NombrePlaces marque) {
        return new RestResponse<>(nombrePlacesRepository.save(marque));
    }
}
