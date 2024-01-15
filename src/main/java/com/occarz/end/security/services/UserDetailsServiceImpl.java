package com.occarz.end.security.services;

import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.repository.user.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Mot de passe ou email incorrect."));

        return UserDetailsImpl.build(utilisateur);
    }
}
