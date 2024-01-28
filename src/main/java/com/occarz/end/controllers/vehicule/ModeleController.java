package com.occarz.end.controllers.vehicule;

import com.occarz.end.dto.requests.vehicule.ModeleRequete;
import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.Marque;
import com.occarz.end.entities.vehicule.Modele;
import com.occarz.end.repository.voiture.MarqueRepository;
import com.occarz.end.repository.voiture.ModeleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/modeles")
@CrossOrigin(origins = "*")
public class ModeleController {
    @Autowired
    ModeleRepository modeleRepository;
    @Autowired
    MarqueRepository marqueRepository;

    @GetMapping("")
    public RestResponse<List<Modele>> allMarques() {
        return new RestResponse<>(modeleRepository.findAll());
    }

    @GetMapping("/{idMarque}")
    public RestResponse<Modele> marqueById(@PathVariable("idMarque") int idMarque) {
        return new RestResponse<>(modeleRepository.findById(idMarque).get());
    }

    @DeleteMapping("/{idMarque}")
    public void deleteMarque(@PathVariable("idMarque") int idMarque) {
        modeleRepository.deleteById(idMarque);
    }

    @PutMapping("")
    public RestResponse<Modele> updateMarque(@RequestBody ModeleRequete marque) {
        Modele modele = modeleRepository.findById(marque.getId()).get();
        Marque marque1 = marqueRepository.findById(marque.getMarque()).get();

        modele.setNom(marque.getNom());
        modele.setMarque(marque1);

        return new RestResponse<>(modeleRepository.save(modele));
    }

    @PostMapping("")
    public RestResponse<Modele> saveMarque(@RequestBody ModeleRequete marque) {
        Marque marque1 = marqueRepository.findById(marque.getMarque()).get();

        Modele modele = new Modele();
        modele.setMarque(marque1);
        modele.setNom(marque.getNom());

        return new RestResponse<>(modeleRepository.save(modele));
    }
}
