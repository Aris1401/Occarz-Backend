package com.occarz.end.entities.configurations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    double commission;
}
