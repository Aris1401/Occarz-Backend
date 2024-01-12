package com.occarz.end.entities.annonce;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.occarz.end.entities.vehicule.AnneeModele;
import com.occarz.end.entities.vehicule.BoiteDeVitesse;
import com.occarz.end.entities.vehicule.Carburant;
import com.occarz.end.entities.vehicule.CategorieVehicule;
import com.occarz.end.entities.vehicule.CouleurVehicule;
import com.occarz.end.entities.vehicule.EtatVehicule;
import com.occarz.end.entities.vehicule.Marque;
import com.occarz.end.entities.vehicule.Modele;
import com.occarz.end.entities.vehicule.NombrePlaces;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class SousAnnonce implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_annonce")
    Annonce annonce;
    
    @ManyToOne()
    @JoinColumn(name = "id_marque")
    Marque marque;

    @ManyToOne()
    @JoinColumn(name = "id_modele")
    Modele modele;
    
    @ManyToOne()
    @JoinColumn(name = "id_annee_modele")
    AnneeModele anneeModele;
    
    @ManyToOne()
    @JoinColumn(name = "id_boite_de_vitesse")
    BoiteDeVitesse boiteDeVitesse;
    
    @ManyToOne()
    @JoinColumn(name = "id_couleur_vehicule")
    CouleurVehicule couleurVehicule;
    
    @ManyToOne()
    @JoinColumn(name = "id_carburant")
    Carburant carburant;
    
    @ManyToOne()
    @JoinColumn(name = "id_categorie_vehicule")
    CategorieVehicule categorieVehicule;

    @ManyToOne()
    @JoinColumn(name = "id_nombre_places")
    NombrePlaces places;

    @ManyToOne
    @JoinColumn(name = "id_etat_vehicule")
    EtatVehicule etatVehicule;
}
