package com.occarz.end.controllers.datas;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.Modele;
import com.occarz.end.repository.voiture.MarqueRepository;
import com.occarz.end.repository.voiture.ModeleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/modeles")
@CrossOrigin(origins = "*")
public class UserModeleController {
    @Autowired
    ModeleRepository modeleRepository;
    @Autowired
    MarqueRepository marqueRepository;

    @GetMapping("")
    public RestResponse<List<Modele>> allMarques() {
        return new RestResponse<>(modeleRepository.findAll());
    }
}
