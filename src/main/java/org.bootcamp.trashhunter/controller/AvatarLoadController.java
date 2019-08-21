package org.bootcamp.trashhunter.controller;

import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class AvatarLoadController {


    @Autowired
    UserService userService;

    @RequestMapping(value = "/image",  produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(Model model, String error, String logout, Principal user) {
        String email = user.getName();
        User user1 = userService.findByEmail(email);
        byte[] image = user1.getPic();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(image, headers, HttpStatus.OK);
    }

}
