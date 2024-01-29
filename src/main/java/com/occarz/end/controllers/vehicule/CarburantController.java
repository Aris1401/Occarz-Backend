package com.occarz.end.controllers.vehicule;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.Carburant;
import com.occarz.end.entities.vehicule.CouleurVehicule;
import com.occarz.end.repository.voiture.CarburantRepository;
import com.occarz.end.repository.voiture.CouleurVehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/carburants")
@CrossOrigin(origins = "*")
public class CarburantController {
    @Autowired
    CarburantRepository carburantRepository;

    @GetMapping("")
    public RestResponse<List<Carburant>> allMarques() {
        return new RestResponse<>(carburantRepository.findAll());
    }

    @GetMapping("/{idMarque}")
    public RestResponse<Carburant> marqueById(@PathVariable("idMarque") int idMarque) {
        return new RestResponse<>(carburantRepository.findById(idMarque).get());
    }

    @DeleteMapping("/{idMarque}")
    public void deleteMarque(@PathVariable("idMarque") int idMarque) {
        carburantRepository.deleteById(idMarque);
    }

    @PutMapping("")
    public RestResponse<Carburant> updateMarque(@RequestBody Carburant marque) {
        return new RestResponse<>(carburantRepository.save(marque));
    }

    @PostMapping("")
    public RestResponse<Carburant> saveMarque(@RequestBody Carburant marque) {
        return new RestResponse<>(carburantRepository.save(marque));
    }
}
