package com.occarz.end.views;

import com.mongodb.annotations.Immutable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Subselect;

@Data
@Entity
@Table(name = "v_details_commission")
@IdClass(VDetailsCommission.class)
@Immutable
@Subselect("select * from v_details_commission")
public class VDetailsCommission {
    @Id
    int annee;
    @Id
    int idMois;
    String moisNom;
    @Column(name = "montant")
    double montant;
}
