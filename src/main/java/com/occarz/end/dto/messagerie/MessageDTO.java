package com.occarz.end.dto.messagerie;

import com.occarz.end.entities.messagerie.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDTO {
    Message message;
    boolean monCote = true;
}
