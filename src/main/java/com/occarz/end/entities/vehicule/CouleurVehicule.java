package com.occarz.end.entities.vehicule;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CouleurVehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String couleur;
    String code;
}
