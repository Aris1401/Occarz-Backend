package com.occarz.end.views;

import com.mongodb.annotations.Immutable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Subselect;

@Entity
@Table(name = "v_annees")
@Immutable
@Data
@Subselect("select * from v_annees")
public class VAnnee {
    @Id
    int annee;
}
