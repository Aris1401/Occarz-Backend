package com.occarz.end.entities.user;

import com.occarz.end.entities.annonce.Annonce;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class RevenuUtilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Date dateRevenu;
    double montant;
    @ManyToOne
    @JoinColumn(name = "id_annonce")
    Annonce annonce;
}
