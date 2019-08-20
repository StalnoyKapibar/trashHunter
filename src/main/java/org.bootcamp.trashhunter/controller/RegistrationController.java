package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.bootcamp.trashhunter.services.MailService;
import org.bootcamp.trashhunter.services.impl.SenderService;
import org.bootcamp.trashhunter.services.impl.TakerService;
import org.bootcamp.trashhunter.services.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.UUID;

@Controller
public class RegistrationController {

    @Autowired
    private MailService mailService;

    @Autowired
    private TakerService takerService;

    @Autowired
    private SenderService senderService;

    @Autowired
    VerificationTokenService verificationTokenService;

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String email, @RequestParam  String password,
                              @RequestParam  String name, @RequestParam  String role) {
        User registeredUser = null;
        if ("TAKER".equals(role)) {
            Taker taker = new Taker();
            taker.setEmail(email);
            taker.setName(name);
            taker.setPassword(password);
            taker.setRegistrationDate(LocalDate.now());
            takerService.add(taker);
            registeredUser = taker;
        } else if ("SENDER".equals(role)) {
            Sender sender = new Sender();
            sender.setEmail(email);
            sender.setName(name);
            sender.setPassword(password);
            sender.setRegistrationDate(LocalDate.now());
            senderService.add(sender);
            registeredUser = sender;
        }
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken =
                new VerificationToken(token, registeredUser, verificationTokenService.calculateExpiryDate());
        verificationTokenService.add(verificationToken);
        //сервис отключен, пока почту не разблокируют
        //mailService.sendMessage(registeredUser, verificationToken);
        return "complited_registration";
    }

    @GetMapping(value = "/activate/{token}")
    public String activate(@PathVariable("token") String token, Model model) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken != null) {
            boolean complete = verificationTokenService.tokenIsNonExpired(verificationToken);
            model.addAttribute("complete", complete);
            if (complete) {
                verificationTokenService.completeRegistration(verificationToken);
            }
        } else {
            model.addAttribute("complete", false);
        }
        return "confirm_registration";
    }
}