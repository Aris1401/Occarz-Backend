package com.occarz.end.entities.annonce;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document(collection = "annonceImages")
public class AnnonceImage {
    @Id
    String id;
    int idAnnonce;
    ArrayList<String> images = new ArrayList<>();
}
