package com.occarz.end.dto.requests;

import com.occarz.end.entities.vehicule.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AjoutAnnonceRequete {
    String titre;
    String description;
    double prix;

    int marque = -1;
    int modele = -1;
    int anneeModele = -1;
    int boiteDeVitesse = -1;
    int couleurVehicule = -1;
    int carburant = -1;
    int categorieVehicule = -1;
    int places = -1;
    int etatVehicule = -1;

    List<String> images = new ArrayList<>();
}
