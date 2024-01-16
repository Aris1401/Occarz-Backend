package com.occarz.end.repository.voiture;

import com.occarz.end.entities.vehicule.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeleRepository extends JpaRepository<Modele, Integer> {
}
