package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.abstraction.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UserRestController {

    @Autowired
    private UserServiceI userService;


    @PostMapping("/change")
    public ResponseEntity changeUserPassword(@RequestParam("new_pass") String password,
                                             @RequestParam("old_pass") String oldPassword) {
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!user.getPassword().equals(oldPassword)) {
            return new ResponseEntity(HttpStatus.BAD_GATEWAY);
        }
        user.setPassword(password);
        userService.update(user);
        return new ResponseEntity(HttpStatus.OK);
    }

}
