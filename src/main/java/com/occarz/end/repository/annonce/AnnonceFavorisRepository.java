package com.occarz.end.repository.annonce;

import com.occarz.end.entities.annonce.AnnonceFavoris;
import com.occarz.end.entities.user.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceFavorisRepository extends JpaRepository<AnnonceFavoris, Integer> {
    public List<AnnonceFavoris> findByUtilisateur(Utilisateur utilisateur);
}
