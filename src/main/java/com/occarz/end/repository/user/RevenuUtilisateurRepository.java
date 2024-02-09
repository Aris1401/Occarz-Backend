package com.occarz.end.repository.user;

import com.occarz.end.entities.user.RevenuUtilisateur;
import com.occarz.end.entities.user.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenuUtilisateurRepository extends JpaRepository<RevenuUtilisateur, Integer> {
}
