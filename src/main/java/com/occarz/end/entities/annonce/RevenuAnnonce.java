package com.occarz.end.entities.annonce;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class RevenuAnnonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Date dateCommission;
    double commission;
    double pourcentageCommission;
    @ManyToOne
    @JoinColumn(name = "id_annonce")
    Annonce annonce;
}
