package org.bootcamp.trashhunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/upload")
public class FileUploadController {


    @PostMapping("/img")
    public ResponseEntity<?> uploadCoursePic(
            @PathVariable Long courseId,
            @RequestParam("file") MultipartFile uploadfile) {

        byte[] arr = null;
        try {
            arr = uploadfile.getBytes();
        } catch (IOException e) {
//            logger.warn("Ошибка обновления аватара курса", courseId);
            return ResponseEntity.status(500).body("Ошибка!");
        }

        if (arr != null) {
            String type = uploadfile.getContentType();
            boolean  isCorrectTpe;

            switch (type) {

                case "image/gif":
                    isCorrectTpe = true;
                    break;
                case "image/jpeg":
                    isCorrectTpe = true;
                    break;
                case "image/png":
                    isCorrectTpe = true;
                    break;
                default:
                    isCorrectTpe = false;
                    break;
            }
//            if (isCorrectTpe) {
//                userService.setPic(courseId, BlobProxy.generateProxy(arr));
//                return ResponseEntity.status(205).build();
//            } else {
//                return ResponseEntity.status(422).build();
//            }
        }
        return ResponseEntity.status(400).build();
    }
}
