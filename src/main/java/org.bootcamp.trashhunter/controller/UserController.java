package org.bootcamp.trashhunter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;

@Controller
public class UserController {

	@GetMapping(value = {"/login"})
	public ModelAndView start() {
		return new ModelAndView("login");
	}

	@GetMapping(value = "/index")
	public ModelAndView index(Model model, String error, String logout, Principal user) {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}

}
