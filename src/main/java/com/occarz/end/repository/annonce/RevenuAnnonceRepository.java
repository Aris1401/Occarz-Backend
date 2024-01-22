package com.occarz.end.repository.annonce;

import com.occarz.end.entities.annonce.RevenuAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenuAnnonceRepository extends JpaRepository<RevenuAnnonce, Integer> {
}
