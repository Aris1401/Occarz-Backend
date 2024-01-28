package com.occarz.end.repository.views;

import com.occarz.end.views.VDetailsVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VDetailsVenteRepository extends JpaRepository<VDetailsVente, Integer> {
    public List<VDetailsVente> findByAnneeAndIdMois(int annee, int idMois);
}
