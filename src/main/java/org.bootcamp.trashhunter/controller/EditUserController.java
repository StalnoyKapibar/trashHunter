package org.bootcamp.trashhunter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

public class EditUserController  {

    @Autowired

    @GetMapping(value = "/edit")
    public ModelAndView index(Model model, String error, String logout, Principal user) {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
