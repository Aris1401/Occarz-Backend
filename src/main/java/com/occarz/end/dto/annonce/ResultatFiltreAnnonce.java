package com.occarz.end.dto.annonce;

import java.util.ArrayList;

import com.occarz.end.entities.annonce.Annonce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultatFiltreAnnonce {
    FiltreAnnonce filtres;
    ArrayList<Annonce> annoncesFiltrees = new ArrayList<>();

    // Nombre de resultats
    public int getNombreResultats() {
        return annoncesFiltrees.size();
    }
}
