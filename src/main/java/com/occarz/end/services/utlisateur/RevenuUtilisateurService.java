package com.occarz.end.services.utlisateur;

import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.user.RevenuUtilisateur;
import com.occarz.end.repository.user.RevenuUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RevenuUtilisateurService {
    @Autowired
    RevenuUtilisateurRepository revenuUtilisateurRepository;

    public double genererRevenueUtilisateurDepuisCommission(Annonce annonce, double commission) {
        // Obtenir total montant pour utilisateur
        RevenuUtilisateur revenuUtilisateur = new RevenuUtilisateur();
        revenuUtilisateur.setDateRevenu(new Date());
        revenuUtilisateur.setAnnonce(annonce);
        revenuUtilisateur.setMontant(obtenirMontantDepuisPrix(annonce.getPrix(), commission));

        // Enregistrer
        revenuUtilisateurRepository.save(revenuUtilisateur);

        return revenuUtilisateur.getMontant();
    }

    public double obtenirMontantDepuisPrix(double prix, double commission) {
        return prix * (1 - (commission / 100));
    }
}
