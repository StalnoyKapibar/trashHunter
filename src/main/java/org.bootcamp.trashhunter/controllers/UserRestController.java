package org.bootcamp.trashhunter.controllers;

import org.apache.coyote.Response;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.AbstractService;
import org.bootcamp.trashhunter.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/succChange")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/succChange")
    @ResponseStatus(HttpStatus.OK)
    public void changeUserPassword(@RequestParam("new_pass") String password,
                                   @RequestParam("old_pass") String oldPassword) {

        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        user.setPassword(password);
        userService.update(user);

    }

}
