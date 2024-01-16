package com.occarz.end.repository.voiture;

import com.occarz.end.entities.vehicule.CouleurVehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouleurVehiculeRepository extends JpaRepository<CouleurVehicule, Integer> {
}
