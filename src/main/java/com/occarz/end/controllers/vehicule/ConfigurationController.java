package com.occarz.end.controllers.vehicule;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.configurations.Configuration;
import com.occarz.end.entities.vehicule.Marque;
import com.occarz.end.repository.configuration.ConfigurationRepository;
import com.occarz.end.repository.voiture.MarqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/configuration")
@CrossOrigin(origins = "*")
public class ConfigurationController {
    @Autowired
    ConfigurationRepository configurationRepository;

    @GetMapping("")
    public RestResponse<List<Configuration>> allMarques() {
        return new RestResponse<>(configurationRepository.findAll());
    }

    @GetMapping("/{idMarque}")
    public RestResponse<Configuration> marqueById(@PathVariable("idMarque") int idMarque) {
        return new RestResponse<>(configurationRepository.findById(idMarque).get());
    }

    @DeleteMapping("/{idMarque}")
    public void deleteMarque(@PathVariable("idMarque") int idMarque) {
        configurationRepository.deleteById(idMarque);
    }

    @PutMapping("")
    public RestResponse<Configuration> updateMarque(@RequestBody Configuration marque) {
        return new RestResponse<>(configurationRepository.save(marque));
    }

    @PostMapping("")
    public RestResponse<Configuration> saveMarque(@RequestBody Configuration marque) {
        return new RestResponse<>(configurationRepository.save(marque));
    }
}
