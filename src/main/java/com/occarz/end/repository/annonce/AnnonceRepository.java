package com.occarz.end.repository.annonce;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.user.Utilisateur;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Integer>, CrudRepository<Annonce, Integer>, PagingAndSortingRepository<Annonce, Integer> {
    // Obtenir annonce par utlisateur
    public List<Annonce> findByUtilisateur(Utilisateur utilisateur);

    // Obtenir les annonces qui ne sont pas a cet utilisateur
    public List<Annonce> findByUtilisateurNot(Utilisateur utilisateur);

    // Obtenir les annonces contenant mot cle
    @Query("select a from Annonce a where lower(a.titre) like %?1% or lower(a.description) like %?1%")
    public List<Annonce> findByTitreOrDescriptionContaining(String motCle);

    // Obtenir les annonces entre deux dates
    public List<Annonce> findByDateAnnonceBetween(Date from, Date to);
    public List<Annonce> findByDateAnnonceGreaterThanEqual(Date from);
    public List<Annonce> findByDateAnnonceLessThanEqual(Date to);

    // Obtenir les annonces par prix
    public List<Annonce> findByPrixBetween(double from, double to);
    public List<Annonce> findByPrixGreaterThanEqual(double from);
    public List<Annonce> findByPrixLessThanEqual(double to);

    // Mettre a jour annonce
    @Modifying
    @Transactional
    @Query("update Annonce set etat = ?2 where id = ?1")
    public int updateStatusAnnonce(int idAnnonce, Annonce.AnnonceState annonceState);
}
