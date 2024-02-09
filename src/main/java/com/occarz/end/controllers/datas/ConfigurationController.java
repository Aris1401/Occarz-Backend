package com.occarz.end.controllers.datas;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.configurations.Configuration;
import com.occarz.end.repository.configuration.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/configuration")
@CrossOrigin(origins = "*")
public class ConfigurationController {
    @Autowired
    ConfigurationRepository configurationRepository;

    @GetMapping("")
    public RestResponse<List<Configuration>> allMarques() {
        return new RestResponse<>(configurationRepository.findAll());
    }
}
