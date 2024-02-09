package com.occarz.end.controllers.datas;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.AnneeModele;
import com.occarz.end.repository.voiture.AnneeModeleRepository;
import com.occarz.end.repository.voiture.ModeleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/anneeModeles")
@CrossOrigin(origins = "*")
public class UserAnneeModeleController {
    @Autowired
    AnneeModeleRepository anneeModeleRepository;

    @Autowired
    ModeleRepository modeleRepository;

    @GetMapping("")
    public RestResponse<List<AnneeModele>> allMarques() {
        return new RestResponse<>(anneeModeleRepository.findAll());
    }
}
