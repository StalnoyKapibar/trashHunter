package org.bootcamp.trashhunter.controllers.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ErrController implements ErrorController {
	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	public ModelAndView error() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("sta", HttpStatus.NOT_FOUND);
		return modelAndView;
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
