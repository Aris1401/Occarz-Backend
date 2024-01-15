package com.occarz.end.security.payload.request;

import com.occarz.end.entities.user.Genre;
import com.occarz.end.entities.user.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ConnexionRequete {
    String email;
    String motDePasse;
}
