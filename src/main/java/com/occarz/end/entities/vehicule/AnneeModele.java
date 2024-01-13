package com.occarz.end.entities.vehicule;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class AnneeModele {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String annee;
    @ManyToOne
    @JoinColumn(name = "id_modele")
    Modele modele;
}
