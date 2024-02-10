package com.occarz.end.dto.messagerie;

import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.user.Utilisateur;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
public class DiscussionDTO {
    String id;
    Date dateDiscussion;
    Utilisateur receveur;
    Annonce annonce;
}
