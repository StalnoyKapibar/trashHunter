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
    public ModelAndView edit(Model model, Principal user) {
        String email = user.getName();
        User user1 = userService.findByEmail(email);
        ModelAndView modelAndView = new ModelAndView();

        if( user1 != null && user1.getClass() == Sender.class) {
            modelAndView.setViewName("/sender/sender_edit_user");
            Sender sender = (Sender) user1;
            model.addAttribute("user", sender);
        } else if (user1 != null && user1.getClass() == Taker.class) {
            modelAndView.setViewName("/taker/taker_edit_user");
            Taker taker = (Taker) user1;
            model.addAttribute("user", taker);
        }
        return modelAndView;
    }
        // Vremeno!
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView saveEditedUser(@RequestParam String name, Principal user,
                                       @RequestParam String about) {
        String emails = user.getName();
        User user1 = userService.findByEmail(emails);
        user1.setName(name);
        user1.setAboutUser(about);
        userService.update(user1);
        ModelAndView mv = new ModelAndView("redirect:/edit");

        return mv;
    }
}
