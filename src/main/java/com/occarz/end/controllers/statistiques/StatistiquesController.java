package com.occarz.end.controllers.statistiques;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.dto.statistiques.VenteMarqueData;
import com.occarz.end.services.statistiques.StatistiqueService;
import com.occarz.end.views.VAnnee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/statistiques")
@CrossOrigin(origins = "*")
public class StatistiquesController {
    @Autowired
    private StatistiqueService statistiqueService;
    
    public double obtenirCommissionSite() {
        // TODO: Implementer
        return 0;
    }

    // Obtenir les annees de statistiques disponibles
    @GetMapping("/annees")
    public RestResponse<List<Integer>> getAnnees() {
        return new RestResponse<>(statistiqueService.getAnnees());
    }

    @GetMapping("/mois")
    public RestResponse<List<String>> getMois() {
        return new RestResponse<>(statistiqueService.getMois());
    }

    @GetMapping(value = "/commissions",params = {"annee"})
    public RestResponse<List<Double>> obtenirCommissions(@RequestParam("annee") int annee) {
        return new RestResponse<>(statistiqueService.getCommissionsParAnnee(annee));
    }

    @GetMapping(value = "/ventes", params = {"annee", "mois"})
    public RestResponse<List<Double>> obtenirVentes(@RequestParam("annee") int annee,
                                                    @RequestParam("mois") int mois) {
        return new RestResponse<>(statistiqueService.getDetailsVente(annee, mois));
    }

    @GetMapping(value = "/ventes/marques", params = {"annee", "mois"})
    public RestResponse<List<VenteMarqueData>> obtenirVentesMarque(@RequestParam("annee") int annee,
                                                                   @RequestParam("mois") int mois) {
        return new RestResponse<>(statistiqueService.getDetailsVenteMarque(annee, mois));
    }

    @GetMapping("/commissions/{annee}")
    public RestResponse<Double> obtenirTotalCommission(@PathVariable("annee") int annee) {
        return new RestResponse<>(statistiqueService.getTotalCommission(annee));
    }
 }
