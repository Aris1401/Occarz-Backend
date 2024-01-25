package com.occarz.end.controllers.messagerie;

import com.occarz.end.dto.messagerie.MessageDTO;
import com.occarz.end.dto.requests.MessageRequete;
import com.occarz.end.dto.response.RestResponse;
import com.occarz.end.entities.annonce.Annonce;
import com.occarz.end.entities.messagerie.Discussion;
import com.occarz.end.entities.messagerie.Message;
import com.occarz.end.services.annonces.AnnonceService;
import com.occarz.end.services.messagerie.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/messagerie")
public class MessagerieController {
    @Autowired
    DiscussionService discussionService;

    @Autowired
    AnnonceService annonceService;

    @GetMapping("/discussions")
    public RestResponse<ArrayList<Discussion>> obtenirDiscussions() {
        return new RestResponse<ArrayList<Discussion>>(discussionService.allDiscussions());
    }

    @PostMapping("/discussions/creer/{idAnnonce}")
    public RestResponse<Discussion> creerDiscussion(@PathVariable int idAnnonce) {
        Annonce annonce = annonceService.findById(idAnnonce);

        try {
            return new RestResponse<>(discussionService.creerUneDiscussionParAnnonce(annonce));
        } catch (Exception e) {
            RestResponse response = new RestResponse<>(null);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @GetMapping("/discussions/{idDiscussion}/messages")
    public RestResponse<List<MessageDTO>> obtenirMessages(@PathVariable int idDiscussion) {
        List<Message> messages = discussionService.obtenirMessagesDiscussion(idDiscussion);
        return new RestResponse<>(discussionService.obtenirCoteesMessages(messages));
    }

    @PostMapping("/discussions/{idDiscussion}/messages")
    public RestResponse<Message> envoyerMessage(@PathVariable int idDiscussion, @RequestBody MessageRequete messageRequete) {
        return new RestResponse<>(discussionService.envoyerMessage(messageRequete.getMessage(), idDiscussion));
    }
}
