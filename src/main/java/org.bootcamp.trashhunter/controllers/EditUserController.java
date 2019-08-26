package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.bootcamp.trashhunter.services.abstraction.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class EditUserController {

    @Autowired
    UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, Principal user) {
        String email = user.getName();
        User user1 = userService.findByEmail(email);

        String user_page = null;
        if( user1 != null && user1.getClass() == Sender.class) {
            user_page = "sender/sender_edit_user";
        } else if (user1 != null && user1.getClass() == Taker.class) {
            user_page = "taker/taker_edit_user";
        }
        model.addAttribute("user", user1);
        return user_page;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEditedUser(@RequestParam String name, Model model, Principal user, @RequestParam String address,
                                 @RequestParam String aboutUser, @RequestParam String phone) {

        String emails = user.getName();
        User user1 = userService.findByEmail(emails);

        String user_page = null;
        if (user1.getClass() == Sender.class) {
            user_page = "sender/sender_edit_user";
        } else if (user1.getClass() == Taker.class) {
            user_page = "taker/taker_edit_user";
        }

        model.addAttribute("user", user1);
        user1.setName(name);
        user1.setAboutUser(aboutUser);
        user1.setPhoneNumber(phone);
        user1.setAddress(address);
        userService.update(user1);

        return user_page;
    }

    @PostMapping("/reset/send_message")
    public String resetPasswordPage(@RequestParam String email, Model model) {
        User registeredUser = userService.findByEmail(email);
        if (registeredUser != null) {
            verificationTokenService.sendTokenToResetPassword(registeredUser);
            model.addAttribute("sentMsg", "ok");
        }
        return "reset/reset_password";
    }

    @GetMapping(value = "/reset/get_token/{token}")
    public String resetPassword(@PathVariable("token") String token, Model model) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken != null) {
            boolean complete = verificationTokenService.tokenIsNonExpired(verificationToken);
            model.addAttribute("complete", complete);
            model.addAttribute("token", token);
            model.addAttribute("email", verificationToken.getUser().getEmail());
        } else {
            model.addAttribute("error", "error");
        }
        return "reset/update_password";
    }

    @GetMapping(value = "/reset/send_message")
    public String resetPasswordPage() {
        return "reset/reset_password";
    }

    @PostMapping("/reset/change_password")
    public ResponseEntity resetUserPassword(@RequestParam("new_pass") String password,
                                            @RequestParam("token") String token,
                                            @RequestParam("email") String email) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken != null && verificationToken.getUser().getEmail().equals(email)) {
            boolean complete = verificationTokenService.tokenIsNonExpired(verificationToken);
            User userByToken = verificationToken.getUser();
            if (userByToken != null && complete) {
                verificationTokenService.completeRegistration(verificationToken);
                userByToken.setPassword(password);
                userService.update(userByToken);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.BAD_GATEWAY);
            }
        } else {
            return new ResponseEntity(HttpStatus.BAD_GATEWAY);
        }
    }
}
