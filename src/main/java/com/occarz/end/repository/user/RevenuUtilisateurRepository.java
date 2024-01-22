package com.occarz.end.repository.user;

import com.occarz.end.entities.user.RevenuUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenuUtilisateurRepository extends JpaRepository<RevenuUtilisateur, Integer> {
}
