package com.occarz.end.controllers.datas;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.Carburant;
import com.occarz.end.repository.voiture.CarburantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/carburants")
@CrossOrigin(origins = "*")
public class CarburantController {
    @Autowired
    CarburantRepository carburantRepository;

    @GetMapping("")
    public RestResponse<List<Carburant>> allMarques() {
        return new RestResponse<>(carburantRepository.findAll());
    }
}
