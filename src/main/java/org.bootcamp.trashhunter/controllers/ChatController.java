package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.config.WebSocketEventListener;
import org.bootcamp.trashhunter.models.ChatMessage;
import org.bootcamp.trashhunter.services.abstraction.SenderService;
import org.bootcamp.trashhunter.services.abstraction.TakerService;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.bootcamp.trashhunter.services.impl.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;

import static java.lang.String.format;


@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessageSendingOperations messagingTemplate;
    private final UserService userService;
    private final ChatMessageService chatMessageService;
    private final SenderService senderService;
    private final TakerService takerService;

    public ChatController(SimpMessageSendingOperations messagingTemplate,
                          UserService userService, ChatMessageService chatMessageService,
                          SenderService senderService, TakerService takerService) {
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
        this.chatMessageService = chatMessageService;
        this.senderService = senderService;
        this.takerService = takerService;
    }


    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId,
                            @Payload ChatMessage chatMessage,
                            Principal principal,
                            Authentication authentication) {
        long takerId = Integer.valueOf(roomId.split("_")[0]);
        long senderId = Integer.valueOf(roomId.split("_")[1]);
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (isRightPrincipal(takerId, senderId, principal, role)) {
            chatMessage.setCreated(LocalDateTime.now());
            messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);

            if (!chatMessage.getType().equals(ChatMessage.MessageType.LASTOFFER)) {
                saveToDB(chatMessage, takerId, senderId);
            }
        }
    }

    @MessageMapping("/chat/{roomId}/addUser")
    public void addUser(@DestinationVariable String roomId, @Payload ChatMessage chatMessage,
                        SimpMessageHeaderAccessor headerAccessor, Principal principal, Authentication authentication) {
        long takerId = Integer.valueOf(roomId.split("_")[0]);
        long senderId = Integer.valueOf(roomId.split("_")[1]);
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (isRightPrincipal(takerId, senderId, principal, role)) {
            String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
            if (currentRoomId != null) {
                ChatMessage leaveMessage = new ChatMessage();
                leaveMessage.setType(ChatMessage.MessageType.LEAVE);
                leaveMessage.setSenderObject(chatMessage.getSenderObject());
                messagingTemplate.convertAndSend(format("/channel/%s", currentRoomId), leaveMessage);
            }
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
            messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
        }
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("partnerId") long partnerId,
                       @RequestParam(value = "offerId", required = false) Long offerId,
                       Principal principal,
                       Authentication authentication,
                       Model model) {
        if (authentication.isAuthenticated()) {
            long currentPrincipalId = userService.findByEmail(principal.getName()).getId();
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            if (role.equals("Taker")) {
                model.addAttribute("username", principal.getName());
                model.addAttribute("chatRoom", currentPrincipalId + "_" + partnerId);
            } else if (role.equals("Sender")) {
                model.addAttribute("username", principal.getName());
                model.addAttribute("chatRoom", partnerId + "_" + currentPrincipalId);
            }
            if (offerId != null) {
                model.addAttribute("offerId", offerId);
            }
        }
        return "chat";
    }

    private boolean isRightPrincipal(long senderId, long takerId, Principal principal, String role) {
        if (role.equals("Taker")) {
            return senderId == userService.findByEmail(principal.getName()).getId();
        } else if (role.equals("Sender")) {
            return takerId == userService.findByEmail(principal.getName()).getId();
        }
        return false;
    }

    private void saveToDB(ChatMessage chatMessage, long takerId, long senderId) {
        chatMessage.setSenderObject(senderService.getById(senderId));
        chatMessage.setTaker(takerService.getById(takerId));
        chatMessageService.add(chatMessage);
    }
}
