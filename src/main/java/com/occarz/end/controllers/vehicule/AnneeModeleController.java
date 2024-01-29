package com.occarz.end.controllers.vehicule;

import com.occarz.end.dto.requests.vehicule.AnneeModeleRequete;
import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.vehicule.AnneeModele;
import com.occarz.end.entities.vehicule.Marque;
import com.occarz.end.entities.vehicule.Modele;
import com.occarz.end.repository.voiture.AnneeModeleRepository;
import com.occarz.end.repository.voiture.MarqueRepository;
import com.occarz.end.repository.voiture.ModeleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/anneeModeles")
@CrossOrigin(origins = "*")
public class AnneeModeleController {
    @Autowired
    AnneeModeleRepository anneeModeleRepository;

    @Autowired
    ModeleRepository modeleRepository;

    @GetMapping("")
    public RestResponse<List<AnneeModele>> allMarques() {
        return new RestResponse<>(anneeModeleRepository.findAll());
    }

    @GetMapping("/{idMarque}")
    public RestResponse<AnneeModele> marqueById(@PathVariable("idMarque") int idMarque) {
        return new RestResponse<>(anneeModeleRepository.findById(idMarque).get());
    }

    @DeleteMapping("/{idMarque}")
    public void deleteMarque(@PathVariable("idMarque") int idMarque) {
        anneeModeleRepository.deleteById(idMarque);
    }

    @PutMapping("")
    public RestResponse<AnneeModele> updateMarque(@RequestBody AnneeModeleRequete marque) {
        Modele modele = modeleRepository.findById(marque.getModele()).get();

        AnneeModele anneeModele = anneeModeleRepository.findById(marque.getId()).get();
        anneeModele.setAnnee(marque.getAnnee());
        anneeModele.setModele(modele);

        return new RestResponse<>(anneeModeleRepository.save(anneeModele));
    }

    @PostMapping("")
    public RestResponse<AnneeModele> saveMarque(@RequestBody AnneeModeleRequete marque) {
        Modele modele = modeleRepository.findById(marque.getModele()).get();

        AnneeModele anneeModele = new AnneeModele();
        anneeModele.setAnnee(marque.getAnnee());
        anneeModele.setModele(modele);

        return new RestResponse<>(anneeModeleRepository.save(anneeModele));
    }
}
