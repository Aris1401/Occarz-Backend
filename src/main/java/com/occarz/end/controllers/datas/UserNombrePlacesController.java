package com.occarz.end.controllers.datas;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.NombrePlaces;
import com.occarz.end.repository.voiture.NombrePlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/nombrePlaces")
@CrossOrigin(origins = "*")
public class UserNombrePlacesController {
    @Autowired
    NombrePlacesRepository nombrePlacesRepository;

    @GetMapping("")
    public RestResponse<List<NombrePlaces>> allMarques() {
        return new RestResponse<>(nombrePlacesRepository.findAll());
    }
}
