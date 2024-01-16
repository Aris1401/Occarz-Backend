package com.occarz.end.repository.voiture;

import com.occarz.end.entities.vehicule.CategorieVehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieVehiculeRepository extends JpaRepository<CategorieVehicule, Integer> {
}
