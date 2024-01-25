package com.occarz.end.entities.messagerie;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Messages")
@Data
public class Message {
    @Id
    int id;
    String message;
    Date dateEnvoie;
    int idUtilisateur;
    int idDiscussion;
}
