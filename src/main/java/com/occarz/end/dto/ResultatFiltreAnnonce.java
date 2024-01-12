package com.occarz.end.dto;

import java.util.ArrayList;

import com.occarz.end.entities.annonce.Annonce;

import lombok.Data;

@Data
public class ResultatFiltreAnnonce {
    FiltreAnnonce filtres;
    ArrayList<Annonce> annoncesFiltrees = new ArrayList<>();
}
