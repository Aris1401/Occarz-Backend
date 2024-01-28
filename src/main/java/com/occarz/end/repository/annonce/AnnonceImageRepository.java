package com.occarz.end.repository.annonce;

import com.occarz.end.entities.annonce.AnnonceImage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface AnnonceImageRepository extends MongoRepository<AnnonceImage, String> {
    public ArrayList<String> findByIdAnnonce(int idAnnonce);
}
