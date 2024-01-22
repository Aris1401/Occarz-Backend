package com.occarz.end.repository.voiture;

import com.occarz.end.entities.vehicule.AnneeModele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface AnneeModeleRepository extends JpaRepository<AnneeModele, Integer> {
}
