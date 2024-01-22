package com.occarz.end.controllers.statistiques;

import com.occarz.end.services.statistiques.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/statistiques")
public class StatistiquesController {
    @Autowired
    private StatistiqueService statistiqueService;
    
    public double obtenirCommissionSite() {
        // TODO: Implementer
        return 0;
    }
}
