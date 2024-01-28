package com.occarz.end.views;

import com.mongodb.annotations.Immutable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Subselect;

@Data
@Immutable
@Entity
@Table(name = "v_details_vente_marque")
@IdClass(VDetailsVenteMarque.class)
@Subselect("select * from v_details_vente_marque")
public class VDetailsVenteMarque {
    @Id
    int annee;
    @Id
    int idMois;
    String moisNom;
    @Id
    int jour;
    @Id
    int idMarque;
    double nombre;
}
