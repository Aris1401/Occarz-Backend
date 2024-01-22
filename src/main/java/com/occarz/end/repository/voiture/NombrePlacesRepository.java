package com.occarz.end.repository.voiture;

import com.occarz.end.entities.vehicule.NombrePlaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NombrePlacesRepository extends JpaRepository<NombrePlaces, Integer> {
}
