package org.bootcamp.trashhunter.services.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.bootcamp.trashhunter.services.abstraction.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freeMarkerConfig;

    private void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    private void sendWithModel(String emailTo, String subject, String message, Map<String, Object> model) {
        model.put("name", emailTo);
        model.put("body", message);
        model.put("link", "http://localhost:8080");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template t = freeMarkerConfig.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(emailTo);
            helper.setText(html, true);
            helper.setSubject(subject);
            helper.setFrom(username);
            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(User user, VerificationToken token) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Привет , %s! \n" +
                            "благодарим за регистрацию на нашем сервисе. Пожалуйста, перейдите по ссылке снизу: " +
                            "<a href=http://localhost:8080/activate/%s>" + "Ваша ссылка :)" + "</a>",
                    user.getName(),
                    token.getToken()

            );
            send(user.getEmail(), "Activatation code", message);
        }
    }

    @Override
    public void sendMessage(User user, String message, String subject) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            send(user.getEmail(), subject, message);
        }
    }
}
