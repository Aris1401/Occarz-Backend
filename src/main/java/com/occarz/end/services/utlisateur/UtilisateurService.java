package com.occarz.end.services.utlisateur;

import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.repository.user.UtilisateurRepository;
import com.occarz.end.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public UserDetailsImpl obtenirDetailsUtilisateurConnecter() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Utilisateur obtenirUtilisateurConnecter() {
        return utilisateurRepository.findById(obtenirDetailsUtilisateurConnecter().getPublicUserInformation().getId()).get();
    }
}
