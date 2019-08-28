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

    @RequestMapping(value = "/profile/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if (user.getId() != id) {
            return "error";
        }
        String page = null;
        if( user != null && user.getClass() == Sender.class) {
            page = "sender/sender_edit_user";
        } else if (user != null && user.getClass() == Taker.class) {
            page = "taker/taker_edit_user";
        }
        model.addAttribute("user", user);
        return page;
    }

    @RequestMapping(value = "/profile/edit/{id}", method = RequestMethod.POST)
    public String saveEditedUser(@RequestParam String aboutUser, @RequestParam String name, Model model,
                                 @RequestParam String address, @PathVariable("id") Long id, @RequestParam String phone,
                                  Principal principal) {
        String emails = principal.getName();
        User user = userService.findByEmail(emails);
        if (user.getId() != id) {
            return "error";
        }
        String user_page = null;
        if (user.getClass() == Sender.class) {
            user_page = "sender/sender_edit_user";
        } else if (user.getClass() == Taker.class) {
            user_page = "taker/taker_edit_user";
        }

        model.addAttribute("user", user);
        model.addAttribute("isEdited", true);
        user.setName(name);
        user.setAboutUser(aboutUser);
        user.setPhoneNumber(phone);
        user.setAddress(address);
        userService.update(user);

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
