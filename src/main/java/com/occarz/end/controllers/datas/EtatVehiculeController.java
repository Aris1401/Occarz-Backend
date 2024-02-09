package com.occarz.end.controllers.datas;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.EtatVehicule;
import com.occarz.end.repository.voiture.EtatVehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/etatVehicules")
@CrossOrigin(origins = "*")
public class EtatVehiculeController {
    @Autowired
    EtatVehiculeRepository etatVehiculeRepository;

    @GetMapping("")
    public RestResponse<List<EtatVehicule>> allMarques() {
        return new RestResponse<>(etatVehiculeRepository.findAll());
    }
}
