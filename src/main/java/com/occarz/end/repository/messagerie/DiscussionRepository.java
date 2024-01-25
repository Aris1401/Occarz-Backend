package com.occarz.end.repository.messagerie;

import com.occarz.end.entities.messagerie.Discussion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends MongoRepository<Discussion, String> {
    @Query("{'$or':[{'idUtilisateurMembre1': ?0},{ 'idUtilisateurMembre2': ?0}]}")
    List<Discussion> findByMembre(int idUser);
}
