package org.bootcamp.trashhunter.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.security.Principal;
import java.time.LocalDate;
import java.util.UUID;

import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.bootcamp.trashhunter.services.impl.SenderService;
import org.bootcamp.trashhunter.services.impl.TakerService;
import org.bootcamp.trashhunter.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {
    @Autowired
    UserService userService;


    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public ModelAndView upload(Principal user, @RequestParam("file") MultipartFile file,Model model) {

        String email = user.getName();
        User user1 = userService.findByEmail(email);
        ModelAndView mv = new ModelAndView("/editUser");



        if( user1 != null && user1.getClass() == Sender.class) {
            Sender sender = (Sender) user1;
            try {
                byte[] bufer = file.getBytes();
                sender.setPic(bufer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addAttribute("user", sender);
        } else if (user1 != null && user1.getClass() == Taker.class) {
            Taker taker = (Taker) user1;
            try {
                byte[] bufer = file.getBytes();
                taker.setPic(bufer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mv.addObject("user", taker);
        }
        return mv;
    }
}
