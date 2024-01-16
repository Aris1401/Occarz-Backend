package com.occarz.end.repository.annonce;

import com.occarz.end.entities.annonce.AnnonceFavoris;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceFavorisRepository extends JpaRepository<AnnonceFavoris, Integer> {
}
