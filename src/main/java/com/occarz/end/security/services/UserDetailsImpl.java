package com.occarz.end.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.occarz.end.dto.user.PublicUserInformation;
import com.occarz.end.entities.user.Utilisateur;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    int id;
    @Getter
    PublicUserInformation publicUserInformation;
    @JsonIgnore
    String motDePasse;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(int id, PublicUserInformation userInformation, String motDePasse, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.publicUserInformation = userInformation;
        this.motDePasse = motDePasse;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Utilisateur utilisateur) {
        List<GrantedAuthority> authorityList = new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority(utilisateur.getRole().name())));
        return new UserDetailsImpl(utilisateur.getId(), new PublicUserInformation(utilisateur), utilisateur.getMotDePasse(), authorityList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    public String getEmail() {
        return publicUserInformation.getEmail();
    }

    @Override
    public String getUsername() {
        return publicUserInformation.getNom();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
