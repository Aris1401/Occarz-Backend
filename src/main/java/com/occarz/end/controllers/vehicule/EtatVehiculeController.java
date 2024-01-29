package com.occarz.end.controllers.vehicule;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.EtatVehicule;
import com.occarz.end.entities.vehicule.NombrePlaces;
import com.occarz.end.repository.voiture.EtatVehiculeRepository;
import com.occarz.end.repository.voiture.NombrePlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/etatVehicules")
@CrossOrigin(origins = "*")
public class EtatVehiculeController {
    @Autowired
    EtatVehiculeRepository etatVehiculeRepository;

    @GetMapping("")
    public RestResponse<List<EtatVehicule>> allMarques() {
        return new RestResponse<>(etatVehiculeRepository.findAll());
    }

    @GetMapping("/{idMarque}")
    public RestResponse<EtatVehicule> marqueById(@PathVariable("idMarque") int idMarque) {
        return new RestResponse<>(etatVehiculeRepository.findById(idMarque).get());
    }

    @DeleteMapping("/{idMarque}")
    public void deleteMarque(@PathVariable("idMarque") int idMarque) {
        etatVehiculeRepository.deleteById(idMarque);
    }

    @PutMapping("")
    public RestResponse<EtatVehicule> updateMarque(@RequestBody EtatVehicule marque) {
        return new RestResponse<>(etatVehiculeRepository.save(marque));
    }

    @PostMapping("")
    public RestResponse<EtatVehicule> saveMarque(@RequestBody EtatVehicule marque) {
        return new RestResponse<>(etatVehiculeRepository.save(marque));
    }
}
