package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class EditUserController  {

    @Autowired
    UserService userService;

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
}
