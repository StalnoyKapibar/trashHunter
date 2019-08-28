package org.bootcamp.trashhunter.controllers.rest;

import org.bootcamp.trashhunter.models.ChatMessage;
import org.bootcamp.trashhunter.services.impl.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ChatRestController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/api/chat/taker/{takerId}/sender/{senderId}")
    public List<ChatMessage> getChatMessage(@PathVariable("takerId") long takerId, @PathVariable("senderId") long senderId) {
        return chatMessageService.getChatByTakerIdAndSenderId(takerId, senderId);
    }

}
