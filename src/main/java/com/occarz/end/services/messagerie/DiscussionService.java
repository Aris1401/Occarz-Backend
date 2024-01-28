package com.occarz.end.services.messagerie;

import com.occarz.end.dto.messagerie.MessageDTO;
import com.occarz.end.dto.user.PublicUserInformation;
import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.messagerie.Discussion;
import com.occarz.end.entities.messagerie.Message;
import com.occarz.end.entities.user.Utilisateur;
import com.occarz.end.repository.messagerie.DiscussionRepository;
import com.occarz.end.repository.messagerie.MessageRepository;
import com.occarz.end.services.annonces.AnnonceService;
import com.occarz.end.services.utlisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DiscussionService {
    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    DiscussionRepository discussionRepository;

    @Autowired
    AnnonceService annonceService;

    @Autowired
    MessageRepository messageRepository;

    /**
     * Creation de discussion a partir d'une annonce.
     * @param annonce Annonce a la quelle on cree la discussion, si il y a.
     * @return La discussion cree.
     */
    public Discussion creerUneDiscussionParAnnonce(Annonce annonce) throws Exception {
        return creerUneDiscussion(annonce.getUtilisateur(), annonce);
    }

    /**
     * Creer une discussion a partir d'une autre utilisateur.
     * @param utilisateur L'utilisateur qu'on veut discuter avec.
     * @param annonce L'annonce a laquelle elle est originaire, peut etre null.
     * @return La discussion cree.
     */
    public Discussion creerUneDiscussion(PublicUserInformation utilisateur, Annonce annonce) throws Exception {
        // Obtenir l'utilisateur connecter
        Utilisateur utilisateurConnectee = utilisateurService.obtenirUtilisateurConnecter();

        // Creer une discussion
        Discussion discussion = new Discussion();
        discussion.setDateDiscussion(new Date());
        if (annonce != null) discussion.setIdAnnonce(annonce.getId());

        discussion.setIdUtilisateurMembre1(utilisateurConnectee.getId());
        discussion.setIdUtilisateurMembre2(utilisateur.getId());
        if (discussion.getIdUtilisateurMembre2() == discussion.getIdUtilisateurMembre1()) throw new Exception("Impossible de creer une discussion.");

        return discussionRepository.save(discussion);
    }

    public ArrayList<Discussion> allDiscussions() {
        // Obtenir utilisateur
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurConnecter();

        // Discussions
        return (ArrayList<Discussion>) discussionRepository.findByMembre(utilisateur.getId());
    }

    ArrayList<Utilisateur> obtenirLesUtilisateursDiscussion(Discussion discussion) {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();

        // Obtenir les utilisateurs
        Utilisateur user1 = utilisateurService.findById(discussion.getIdUtilisateurMembre1());
        Utilisateur user2 = utilisateurService.findById(discussion.getIdUtilisateurMembre2());

        utilisateurs.add(user1);
        utilisateurs.add(user2);

        return utilisateurs;
    }

    public Utilisateur obtenirInterlocuteur(Discussion discussion) {
        // Obtenir l'utilisateur connectee
        Utilisateur connectedUser = utilisateurService.obtenirUtilisateurConnecter();

        // Obtenir les membres de la discussion
        ArrayList<Utilisateur> users = obtenirLesUtilisateursDiscussion(discussion);
        for (Utilisateur user : users) {
            if (user.getId() != connectedUser.getId()) return user;
        }

        return null;
    }

    public Annonce obtenirAnnonce(Discussion discussion) {
        return annonceService.findById(discussion.getIdAnnonce());
    }

    // Messages
    public List<Message> obtenirMessagesDiscussion(String idDiscussion) {
        return messageRepository.findByIdDiscussion(idDiscussion);
    }

    public List<MessageDTO> obtenirCoteesMessages(List<Message> messages) {
        // Obtenir l'utilisateur connecterr
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurConnecter();

        List<MessageDTO> messageDTOS = new ArrayList<>();

        for (Message message : messages) {
            MessageDTO messageDTO = new MessageDTO(message, message.getIdUtilisateur() == utilisateur.getId());
            messageDTOS.add(messageDTO);
        }

        return messageDTOS;
    }

    public Message envoyerMessage(String messageTexte, String idDiscussion) {
        // Obtenir l'utilisateur connecter
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurConnecter();

        // Creation message
        Message message = new Message();
        message.setMessage(messageTexte);
        message.setIdDiscussion(idDiscussion);
        message.setDateEnvoie(new Date());
        message.setIdUtilisateur(utilisateur.getId());

        return messageRepository.save(message);
    }
}
