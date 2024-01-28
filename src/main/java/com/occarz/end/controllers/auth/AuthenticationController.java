package com.occarz.end.controllers.auth;

import com.occarz.end.security.payload.request.ConnexionRequete;
import com.occarz.end.security.payload.request.InscriptionRequete;
import com.occarz.end.security.payload.request.RefreshTokenRequete;
import com.occarz.end.security.payload.response.JwtResponse;
import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.security.payload.response.TokenRefreshResponse;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.repository.user.GenreRepository;
import com.occarz.end.repository.user.UtilisateurRepository;
import com.occarz.end.security.entities.RefreshToken;
import com.occarz.end.security.jwt.JwtUtils;
import com.occarz.end.security.services.RefreshTokenService;
import com.occarz.end.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3000)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/connexion")
    public JwtResponse connextionUtilisateur(@RequestBody ConnexionRequete connexionRequete) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(connexionRequete.getEmail(), connexionRequete.getMotDePasse()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList()).get(0);

        refreshTokenService.deleteByUserId(userDetails.getPublicUserInformation().getId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getPublicUserInformation().getId());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwt);
        jwtResponse.setRefreshToken(refreshToken.getToken());
        jwtResponse.setData(role);

        return jwtResponse;
    }

    @PostMapping("/inscription")
    public RestResponse<String> inscriptionUtilisateur(@RequestBody InscriptionRequete inscriptionRequete) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(inscriptionRequete.getNom());
        utilisateur.setPrenom(inscriptionRequete.getPrenom());
        utilisateur.setDateNaissance(inscriptionRequete.getDateNaissance());
        utilisateur.setMotDePasse(encoder.encode(inscriptionRequete.getMotDePasse()));
        utilisateur.setGenre(genreRepository.findById(inscriptionRequete.getGenre()).get());
        utilisateur.setEmail(inscriptionRequete.getEmail());
        utilisateur.setContact(inscriptionRequete.getContact());
        utilisateur.setPhotoProfil(inscriptionRequete.getPhotoProfil());
        utilisateur.setRole(Utilisateur.RoleUtilisateur.USER);
        utilisateur.setDateCreation(new Date());

        utilisateurRepository.save(utilisateur);

        return new RestResponse<>("Compte creer");
    }

    @PostMapping("/refreshtoken")
    public TokenRefreshResponse refreshToken(@RequestBody RefreshTokenRequete refreshTokenRequete) throws Exception {
        String refreshToken = refreshTokenRequete.getRefreshToken();

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUtilisateur)
                .map(utilisateur -> {
                    String token = jwtUtils.generateJwtTokenFromEmail(utilisateur.getEmail());

                    TokenRefreshResponse tokenRefreshResponse = new TokenRefreshResponse();
                    tokenRefreshResponse.setAccessToken(token);
                    tokenRefreshResponse.setRefreshToken(refreshToken);

                    return tokenRefreshResponse;
                }).orElseThrow(() ->
                   new Exception("Refresh token invalide.")
                );
    }

    @PostMapping("/deconnexion")
    public RestResponse<String> deconnexion() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int idUtilisateur = userDetails.getPublicUserInformation().getId();
        refreshTokenService.deleteByUserId(idUtilisateur);

        return new RestResponse<>("Utilisateur deconnecter.");
    }
}
