package com.occarz.end.controllers.datas;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.CouleurVehicule;
import com.occarz.end.repository.voiture.CouleurVehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/couleurVehicules")
@CrossOrigin(origins = "*")
public class UserCouleurVehiculeController {
    @Autowired
    CouleurVehiculeRepository couleurVehiculeRepository;

    @GetMapping("")
    public RestResponse<List<CouleurVehicule>> allMarques() {
        return new RestResponse<>(couleurVehiculeRepository.findAll());
    }

    @GetMapping("/{idMarque}")
    public RestResponse<CouleurVehicule> marqueById(@PathVariable("idMarque") int idMarque) {
        return new RestResponse<>(couleurVehiculeRepository.findById(idMarque).get());
    }

    @DeleteMapping("/{idMarque}")
    public void deleteMarque(@PathVariable("idMarque") int idMarque) {
        couleurVehiculeRepository.deleteById(idMarque);
    }

    @PutMapping("")
    public RestResponse<CouleurVehicule> updateMarque(@RequestBody CouleurVehicule marque) {
        return new RestResponse<>(couleurVehiculeRepository.save(marque));
    }

    @PostMapping("")
    public RestResponse<CouleurVehicule> saveMarque(@RequestBody CouleurVehicule marque) {
        return new RestResponse<>(couleurVehiculeRepository.save(marque));
    }
}
