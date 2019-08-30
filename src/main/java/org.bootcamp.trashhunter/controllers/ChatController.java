package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.config.WebSocketEventListener;
import org.bootcamp.trashhunter.models.*;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.bootcamp.trashhunter.services.abstraction.SenderService;
import org.bootcamp.trashhunter.services.abstraction.TakerService;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.bootcamp.trashhunter.services.impl.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.String.format;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessageSendingOperations messagingTemplate;
    private final UserService userService;
    private final ChatMessageService chatMessageService;
    private final SenderService senderService;
    private final TakerService takerService;
    private final OfferService offerService;

    @Autowired
    public ChatController(SimpMessageSendingOperations messagingTemplate,
                          UserService userService, ChatMessageService chatMessageService,
                          SenderService senderService, TakerService takerService, OfferService offerService) {
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
        this.chatMessageService = chatMessageService;
        this.senderService = senderService;
        this.takerService = takerService;
        this.offerService = offerService;
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
    public void addUser(@DestinationVariable String roomId,
                        @Payload ChatMessage chatMessage,
                        SimpMessageHeaderAccessor headerAccessor,
                        Principal principal,
                        Authentication authentication) {
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
    public String chat(@RequestParam(value = "offerId", required = false) Long offerId,
                       Principal principal,
                       Model model) {
        User chatOwner = userService.findByEmail(principal.getName());
        model.addAttribute("chatOwner", chatOwner);

        Map<User, Collection<Offer>> companionsWithOffersMap = getCompanionsWithOffers(chatOwner);
        if (!companionsWithOffersMap.isEmpty()) {
            model.addAttribute("companionsWithOffersMap", companionsWithOffersMap);
        }
        if (offerId != null) {
            model.addAttribute("offerId", offerId);
        }
        return "chat";
    }

    @GetMapping("/chat/{companionId}")
    public String chat(@PathVariable long companionId,
                       @RequestParam(value = "offerId", required = false) Long offerId,
                       Principal principal,
                       Authentication authentication,
                       Model model) {
        User chatOwner = userService.findByEmail(principal.getName());
        long ownerId = chatOwner.getId();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("username", principal.getName());
        if (role.equals("Taker")) {
            model.addAttribute("chatRoom", ownerId + "_" + companionId);
        } else if (role.equals("Sender")) {
            model.addAttribute("chatRoom", companionId + "_" + ownerId);
        }

        model.addAttribute("companionId", companionId);
        model.addAttribute("chatOwner", chatOwner);
        Map<User, Collection<Offer>> companionsWithOffersMap = getCompanionsWithOffers(chatOwner);
        if (!companionsWithOffersMap.isEmpty()) {
            model.addAttribute("companionsWithOffersMap", companionsWithOffersMap);
        }
        if (offerId != null) {
            model.addAttribute("offerId", offerId);
        }
        return "chat";
    }

    private Map<User, Collection<Offer>> getCompanionsWithOffers(User chatOwner) {
        String chatOwnerRole = chatOwner.getClass().getSimpleName();
        Map<User, Collection<Offer>> companionsWithOffersMap = new HashMap<>();

        if (chatOwnerRole.equals("Taker")) {
            List<Offer> takerTakenOffersList = offerService.getTakenOffersByTaker((Taker) chatOwner);
            takerTakenOffersList.forEach(x -> {
                Sender sender = x.getSender();
                if (!companionsWithOffersMap.containsKey(x.getSender())) {
                    List<Offer> offerList = new ArrayList<>();
                    offerList.add(x);
                    companionsWithOffersMap.put(sender, offerList);
                } else {
                    companionsWithOffersMap.get(sender).add(x);
                }
            });
        } else if (chatOwnerRole.equals("Sender")) {
            List<Offer> senderTakenOffersList = offerService.getTakenOffersBySender((Sender) chatOwner);
            senderTakenOffersList.forEach(x -> {
                Taker taker = x.getRespondingTakers().get(0);
                if (!companionsWithOffersMap.containsKey(taker)) {
                    List<Offer> offerList = new ArrayList<>();
                    offerList.add(x);
                    companionsWithOffersMap.put(taker, offerList);
                } else {
                    companionsWithOffersMap.get(taker).add(x);
                }
            });
        }
        return companionsWithOffersMap;
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
