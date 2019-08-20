package org.bootcamp.trashhunter.controllers;

import org.apache.coyote.Response;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/succChange")
public class UserRestController {

//    @Autowired
//    private UserService userService;

    @PostMapping("/succChange")
    @ResponseStatus(HttpStatus.OK)
    public void changeUserPassword(@RequestParam("password") String password,
                                                @RequestParam("oldpassword") String oldPassword) {
//        User user =
    }

}
