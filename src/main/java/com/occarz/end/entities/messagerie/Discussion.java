package com.occarz.end.entities.messagerie;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "discussions")
@Data
public class Discussion {
    @Id
    String id;
    Date dateDiscussion;
    int idUtilisateurMembre1;
    int idUtilisateurMembre2;
    int idAnnonce = -1;
}
