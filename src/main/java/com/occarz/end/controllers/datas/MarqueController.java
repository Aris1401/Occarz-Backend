package com.occarz.end.controllers.datas;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.Marque;
import com.occarz.end.repository.voiture.MarqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/marques")
@CrossOrigin(origins = "*")
public class MarqueController {
    @Autowired
    MarqueRepository marqueRepository;

    @GetMapping("")
    public RestResponse<List<Marque>> allMarques() {
        return new RestResponse<>(marqueRepository.findAll());
    }

    @GetMapping("/{idMarque}")
    public RestResponse<Marque> marqueById(@PathVariable("idMarque") int idMarque) {
        return new RestResponse<>(marqueRepository.findById(idMarque).get());
    }
}
