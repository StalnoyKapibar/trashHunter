package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.config.WebSocketEventListener;
import org.bootcamp.trashhunter.models.*;
import org.bootcamp.trashhunter.models.dto.UserDto;
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
import java.util.stream.Collectors;

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
                       Authentication authentication,
                       Model model) {
        User user = userService.findByEmail(principal.getName());
        UserDto chatOwner = new UserDto(user);
        model.addAttribute("chatOwner", chatOwner);
        Collection<User> companionList = getCompanionList(user);
        if (!companionList.isEmpty()) {
            model.addAttribute("companionList", companionList);
        }
        if (offerId != null) {
            model.addAttribute("offerId", offerId);
        }
        Map<User, Collection<Offer>> companionOffersMap = getCompanionToOffersMap(user, companionList);
        return "chat";
    }

    @GetMapping("/chat/{companionId}")
    public String chat(@PathVariable long companionId,
                       @RequestParam(value = "offerId", required = false) Long offerId,
                       Principal principal,
                       Authentication authentication,
                       Model model) {
        long ownerId = userService.findByEmail(principal.getName()).getId();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals("Taker")) {
            model.addAttribute("username", principal.getName());
            model.addAttribute("chatRoom", ownerId + "_" + companionId);
        } else if (role.equals("Sender")) {
            model.addAttribute("username", principal.getName());
            model.addAttribute("chatRoom", companionId + "_" + ownerId);
        }

        User user = userService.findByEmail(principal.getName());
        UserDto chatOwner = new UserDto(user);
        model.addAttribute("chatOwner", chatOwner);
        Collection<User> companionList = getCompanionList(user);
        if (!companionList.isEmpty()) {
            model.addAttribute("companionList", companionList);
        }
        if (offerId != null) {
            model.addAttribute("offerId", offerId);
        }
        return "chat";
    }

    private Collection<User> getCompanionList(User chatOwner) {
        String role = chatOwner.getClass().getSimpleName();
        Collection<User> companionList = new HashSet<>();
        if (role.equals("Sender")) {
            Map<Offer, List<Taker>> offersBySenderIdActiveFirst = offerService.getOffersBySenderIdActiveFirst(chatOwner.getEmail());
            for (Map.Entry<Offer, List<Taker>> entry : offersBySenderIdActiveFirst.entrySet()) {
                if (entry.getValue().size() == 1) {
                    companionList.add(entry.getValue().get(0));
                }
            }
        } else if (role.equals("Taker")) {
            List<Offer> takerOffersList = offerService.getOffersByTaker(chatOwner.getEmail());
            for (Offer offer : takerOffersList) {
                companionList.add(offer.getSender());
            }
        }
        return companionList;
    }
/*
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
*/

    private Map<User, Collection<Offer>> getCompanionToOffersMap(User chatOwner, Collection<User> companionList) {
        String companionRole = companionList.iterator().next().getClass().getSimpleName();
        Map<User, Collection<Offer>> companionToOffersMap = new HashMap<>();

        if (companionRole.equals("Sender")) {
            for (User companion : companionList) {
                List<Offer> companionOffersList = offerService.getOffersByTaker(companion.getEmail());
                companionOffersList = companionOffersList.stream()
                        .filter(offer ->
                            offer.getOfferStatus().equals(OfferStatus.TAKEN) &&
                            offer.getRespondingTakers().contains((Taker) chatOwner))
                        .collect(Collectors.toList());
                companionToOffersMap.put(companion, companionOffersList);
            }
        } else if (companionRole.equals("Taker")) {
            for (User companion : companionList) {
                List<Offer> companionOffers = offerService.getOffersByTaker(companion.getEmail());
                companionOffers = companionOffers.stream()
                        .filter(offer -> offer.getSender().equals((Sender) chatOwner))
                        .collect(Collectors.toList());
                companionToOffersMap.put(companion, companionOffers);
            }
        }
        return companionToOffersMap;
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
