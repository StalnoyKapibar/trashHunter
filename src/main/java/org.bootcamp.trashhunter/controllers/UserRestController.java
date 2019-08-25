package org.bootcamp.trashhunter.controllers;

import org.apache.coyote.Response;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.bootcamp.trashhunter.services.AbstractService;
import org.bootcamp.trashhunter.services.impl.MailService;
import org.bootcamp.trashhunter.services.impl.UserService;
import org.bootcamp.trashhunter.services.impl.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    VerificationTokenService verificationTokenService;


    @PostMapping("/change_password")
    public ResponseEntity changeUserPassword(@RequestParam("new_pass") String password,
                                             @RequestParam("old_pass") String oldPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()){
            User user = userService.findByEmail(authentication.getName());
            user.setPassword(password);
            userService.update(user);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/resend_email_for_token_recovery")
    public ResponseEntity resendEmail(@RequestParam("address") String address) {
        User user = userService.findByEmail(address);
        if ((user != null) && (!user.isEnabled())) {
            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken =
                    new VerificationToken(token, user, verificationTokenService.calculateExpiryDate());
            verificationTokenService.add(verificationToken);
            mailService.sendMessage(user, verificationToken);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_GATEWAY);
        }
    }
}
