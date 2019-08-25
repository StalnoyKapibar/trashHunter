package org.bootcamp.trashhunter.controllers.rest;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
public class FileUploadController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/editPhoto", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity updateSeekerPhoto(@RequestParam(value = "img") MultipartFile image, Principal user) {
        String email = user.getName();
        User user1 = userServiceImpl.findByEmail(email);

        try {
            byte[] photo = image.getBytes();
            user1.setPic(photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userServiceImpl.update(user1);
        return new ResponseEntity(HttpStatus.OK);
    }
}