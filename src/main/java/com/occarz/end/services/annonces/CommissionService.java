package com.occarz.end.services.annonces;

import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.annonce.RevenuAnnonce;
import com.occarz.end.repository.annonce.RevenuAnnonceRepository;
import com.occarz.end.repository.configuration.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommissionService {
    @Autowired
    RevenuAnnonceRepository revenuAnnonceRepository;
    @Autowired
    ConfigurationRepository configurationRepository;
    public double ajouterCommissionDepuisAnnonce(Annonce annonce) {
        // Obtenir commission du site
        double commission = configurationRepository.findAll().get(0).getCommission();

        // Creer revenu pour site
        RevenuAnnonce revenuAnnonce = new RevenuAnnonce();
        revenuAnnonce.setCommission(calculerAnnonceFromPrix(annonce.getPrix(), commission));
        revenuAnnonce.setPourcentageCommission(commission);
        revenuAnnonce.setDateCommission(new Date());

        revenuAnnonceRepository.save(revenuAnnonce);

        return commission;
    }

    public double calculerAnnonceFromPrix(double prix, double commission) {
        return prix * (commission / 100.0);
    }
}
