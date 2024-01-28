package com.occarz.end.services.statistiques;

import com.occarz.end.dto.statistiques.VenteMarqueData;
import com.occarz.end.entities.configurations.Mois;
import com.occarz.end.entities.vehicule.Marque;
import com.occarz.end.repository.configuration.MoisRepository;
import com.occarz.end.repository.views.*;
import com.occarz.end.repository.voiture.MarqueRepository;
import com.occarz.end.views.VAnnee;
import com.occarz.end.views.VDetailsCommission;
import com.occarz.end.views.VDetailsVente;
import com.occarz.end.views.VDetailsVenteMarque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatistiqueService {
    // Annee repository
    @Autowired
    VAnneeRepository vAnneeRepository;
    @Autowired
    MoisRepository moisRepository;
    @Autowired
    VDetailsCommissionRepository detailsCommissionRepository;
    @Autowired
    VDetailsVenteRepository detailsVenteRepository;
    @Autowired
    VDetailsVenteMarqueRepository detailsVenteMarqueRepository;
    @Autowired
    MarqueRepository marqueRepository;
    @Autowired
    VTotalCommissionRepository totalCommissionRepository;

    public double getTotalCommission(int annee) {
        return totalCommissionRepository.findById(annee).get().getCommissionTotal();
    }

    public List<Integer> getAnnees() {
        List<VAnnee> annees = vAnneeRepository.findAll();
        return annees.stream().map(annee -> {
            return annee.getAnnee();
        }).collect(Collectors.toList());
    }

    public List<String> getMois() {
        List<Mois> mois = moisRepository.findAll();
        return mois.stream().map(mois1 -> {
            return mois1.getNom();
        }).collect(Collectors.toList());
    }

    public List<Double> getCommissionsParAnnee(int annee) {
        List<VDetailsCommission> detailsCommissions = detailsCommissionRepository.findByAnnee(annee);
        return detailsCommissions.stream().map(details -> {
            return details.getMontant();
        }).collect(Collectors.toList());
    }

    public List<Double> getDetailsVente(int annee, int mois) {
        List<VDetailsVente> detailsVentes = detailsVenteRepository.findByAnneeAndIdMois(annee, mois);
        return detailsVentes.stream().map(details -> {
            return details.getNombre();
        }).collect(Collectors.toList());
    }

    public List<VenteMarqueData> getDetailsVenteMarque(int annee, int mois) {
        List<Marque> marques = marqueRepository.findAll();
        List<VDetailsVenteMarque> vDetailsVenteMarques = detailsVenteMarqueRepository.findByAnneeAndIdMois(annee, mois);

        return marques.stream().map(marque -> {
            VenteMarqueData venteMarqueData = new VenteMarqueData();
            venteMarqueData.setName(marque.getNom());

            // Vente data
            List<Double> data = vDetailsVenteMarques.stream().
                    filter(details -> details.getIdMarque() == marque.getId()).
                    map(details -> {
                        return details.getNombre();
            }).collect(Collectors.toList());
            venteMarqueData.setData(data);

            return venteMarqueData;
        }).collect(Collectors.toList());
    }
}
