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
@Table(name = "v_total_commission")
@Subselect("select * from v_total_commission")
public class VTotalCommission {
    @Id
    int annee;
    double commissionTotal;
}
