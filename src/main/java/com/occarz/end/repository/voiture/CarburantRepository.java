package com.occarz.end.repository.voiture;

import com.occarz.end.entities.vehicule.Carburant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarburantRepository extends JpaRepository<Carburant, Integer> {
}
