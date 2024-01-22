package com.occarz.end.repository.annonce;

import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.annonce.AnnonceFavoris;
import com.occarz.end.entities.user.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnnonceFavorisRepository extends JpaRepository<AnnonceFavoris, Integer> {
    // Obtenir les annonces favoris d'un utilisateur
    public List<AnnonceFavoris> findByUtilisateur(Utilisateur utilisateur);

    // Enlever annonce favoris d'un utilisateur
    @Modifying
    @Transactional
    public int deleteByAnnonceAndUtilisateur(Annonce annonce, Utilisateur utilisateur);
}
