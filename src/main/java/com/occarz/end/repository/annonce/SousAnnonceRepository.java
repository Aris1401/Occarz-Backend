package com.occarz.end.repository.annonce;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.annonce.SousAnnonce;

@Repository
public interface SousAnnonceRepository extends CrudRepository<SousAnnonce, Integer>, JpaRepository<SousAnnonce, Integer>, PagingAndSortingRepository<SousAnnonce, Integer> {
    // Obtenir sous annonces d'une annonce
    public SousAnnonce findByAnnonce(Annonce annonce);
}
