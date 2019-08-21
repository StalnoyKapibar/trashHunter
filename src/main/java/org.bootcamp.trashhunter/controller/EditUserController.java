package org.bootcamp.trashhunter.controller;

import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class EditUserController  {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(Model model, Principal user) {
        String email = user.getName();
        User user1 = userService.findByEmail(email);
        ModelAndView mv = new ModelAndView("edit_user2_test");

        if( user1 != null && user1.getClass() == Sender.class) {
            Sender sender = (Sender) user1;
            model.addAttribute("user", sender);
        } else if (user1 != null && user1.getClass() == Taker.class) {
            Taker taker = (Taker) user1;
            mv.addObject("user", taker);
        }
        return mv;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView saveEditedUser(@RequestParam String name, Principal user,
                                       @RequestParam String email) {
        String emails = user.getName();
        User user1 = userService.findByEmail(emails);
        user1.setName(name);
        user1.setEmail(email);
        userService.update(user1);

        ModelAndView mv = new ModelAndView("redirect:/edit");

        return mv;
    }
}
