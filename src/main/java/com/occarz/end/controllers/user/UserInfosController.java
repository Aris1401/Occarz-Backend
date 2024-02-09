package com.occarz.end.controllers.user;

import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.user.RevenuUtilisateur;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.repository.user.RevenuUtilisateurRepository;
import com.occarz.end.services.utlisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user/infos")
public class UserInfosController {
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    RevenuUtilisateurRepository revenuUtilisateurRepository;
    @GetMapping("")
    public RestResponse<Utilisateur> obtenirUtilisateur() {
        return new RestResponse<>(utilisateurService.obtenirUtilisateurConnecter());
    }


}
