package com.occarz.end.repository.voiture;

import com.occarz.end.entities.vehicule.EtatVehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatVehiculeRepository extends JpaRepository<EtatVehicule, Integer> {
}
