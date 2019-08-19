package org.bootcamp.trashhunter.controllers;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Set;

@Controller
public class MainController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(Model model, String error, String logout, Principal user) {
        ModelAndView mv = new ModelAndView("/index");
        if (user != null) {
            return mv;
        }
        return new ModelAndView("/login");
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(Model model, String error, String logout, Authentication authentication, SecurityContextHolderAwareRequestWrapper request) {
        boolean b = request.isUserInRole("Sender");
        System.out.println(b);
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (authentication.isAuthenticated()) {
            if (authorities.contains("Sender")) {
                ModelAndView mv = new ModelAndView("sender/sender_page");
                return mv;
            } else {
                ModelAndView mv = new ModelAndView("taker/taker_page");
                return mv;
            }
        } else {
            ModelAndView mv = new ModelAndView("index");
            return mv;
        }
    }


    @GetMapping("/sender_my_offers")
    public String senderMyOffers() {
        return "sender/sender_my_offers";
    }
}
