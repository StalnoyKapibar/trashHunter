package org.bootcamp.trashhunter.controller;

import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.bootcamp.trashhunter.services.abstraction.MailServiceI;
import org.bootcamp.trashhunter.services.abstraction.SenderServiceI;
import org.bootcamp.trashhunter.services.abstraction.TakerServiceI;
import org.bootcamp.trashhunter.services.impl.tokens.VerificationTokenService;
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
    private MailServiceI mailServiceI;

    @Autowired
    private TakerServiceI takerServiceImpl;

    @Autowired
    private SenderServiceI senderServiceImpl;

    @Autowired
    VerificationTokenService verificationTokenService;

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration/registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String email, @RequestParam  String password,
                               @RequestParam  String name, @RequestParam  String role) {
        // todo duplicate( will be done after merge by Matvey)
        User registeredUser = null;
        if ("TAKER".equals(role)) {
            Taker taker = new Taker();
            taker.setEmail(email);
            taker.setName(name);
            taker.setPassword(password);
            taker.setRegistrationDate(LocalDate.now());
            takerServiceImpl.add(taker);
            registeredUser = taker;
        } else if ("SENDER".equals(role)) {
            Sender sender = new Sender();
            sender.setEmail(email);
            sender.setName(name);
            sender.setPassword(password);
            sender.setRegistrationDate(LocalDate.now());
            senderServiceImpl.add(sender);
            registeredUser = sender;
        }

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken =
                new VerificationToken(token, registeredUser, verificationTokenService.calculateExpiryDate());
        verificationTokenService.add(verificationToken);
        mailServiceI.sendMessage(registeredUser, verificationToken);

        return "registration/complited_registration";
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
        return "registration/confirm_registration";
    }
}