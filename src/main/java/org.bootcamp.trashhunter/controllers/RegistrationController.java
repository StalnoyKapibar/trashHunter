package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.bootcamp.trashhunter.services.abstraction.MailService;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.bootcamp.trashhunter.services.abstraction.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RegistrationController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration/registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String email, @RequestParam  String password, @RequestParam  String name,
                               @RequestParam  String role, @RequestParam  String city) {
        byte [] pic = userService.extractBytesDefaultAvatar();
        User registeredUser = null;
        if ("TAKER".equals(role)) {
            registeredUser = new Taker(email, name, password, LocalDate.now(), city, pic );
        } else if ("SENDER".equals(role)) {
            registeredUser = new Sender(email, name, password, LocalDate.now() ,city, pic);
        }
        userService.add(registeredUser);
        verificationTokenService.sendToken(registeredUser);
        return "registration/completed_registration";
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