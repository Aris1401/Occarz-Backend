package com.occarz.end.repository.views;

import com.occarz.end.views.VDetailsVenteMarque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VDetailsVenteMarqueRepository extends JpaRepository<VDetailsVenteMarque, Integer> {
    public List<VDetailsVenteMarque> findByAnneeAndIdMois(int annee, int idMois);
}
