package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.dto.StaticticsDto;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class UserProfileController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public String getUserPage(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        StaticticsDto staticticsDto = StaticticsDto.getStaticticsDto(user.getStatistics());
        model.addAttribute("statistic", staticticsDto);
        model.addAttribute("user", user);
        model.addAttribute("isHolder", true);
        return "user_profile";
    }

    @GetMapping("/profile/{id}")
    public String getUserPage(@PathVariable("id") Long id, Model model, Principal principal) {
        User userFromSession = userService.findByEmail(principal.getName());
        StaticticsDto staticticsDto = null;
        if (id == userFromSession.getId()) {
            model.addAttribute("isHolder", true);
            model.addAttribute("user", userFromSession);
            staticticsDto = StaticticsDto.getStaticticsDto(userFromSession.getStatistics());
        } else {
            User userFromPath = userService.findById(id);
            model.addAttribute("user", userFromPath);
            staticticsDto = StaticticsDto.getStaticticsDto(userFromPath.getStatistics());
        }
        model.addAttribute("statistic", staticticsDto);
        return "user_profile";
    }
}
