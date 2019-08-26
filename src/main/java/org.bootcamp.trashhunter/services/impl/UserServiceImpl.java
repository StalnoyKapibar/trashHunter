package org.bootcamp.trashhunter.services.impl;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.bootcamp.trashhunter.dao.abstraction.UserDao;
import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.AbstractServiceImpl;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

    @Autowired
    private UserDao userDao;

    public User findByEmail(String email) {
        return  userDao.findByEmail(email);
    }

    public User findById(long id) {
        return  userDao.findById(id);
    }

    public String encoder64(String string) { return   userDao.base64Encoder(string); }

    public byte[] extractBytesDefaultAvatar () {
        File imgPath = new File("C:\\Users\\Iura\\Documents\\code\\trashHunter\\src\\main\\resources\\static\\img\\defaultAvatar.png");
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteOutputStream bos = null;
        try {
            bos = new ByteOutputStream();
            ImageIO.write(bufferedImage, "png", bos);
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            try {
                bos.close();
            } catch (Exception e) {
            }
        }
        return bos == null ? null : bos.getBytes();
    }
}
