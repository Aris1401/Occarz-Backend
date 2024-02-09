package com.occarz.end.controllers.datas;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.CategorieVehicule;
import com.occarz.end.repository.voiture.CategorieVehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/categorieVehicules")
@CrossOrigin(origins = "*")
public class CategorieVehiculeController {
    @Autowired
    CategorieVehiculeRepository categorieVehiculeRepository;

    @GetMapping("")
    public RestResponse<List<CategorieVehicule>> allMarques() {
        return new RestResponse<>(categorieVehiculeRepository.findAll());
    }
}
