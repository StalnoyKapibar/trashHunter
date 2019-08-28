package org.bootcamp.trashhunter.security;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        HttpSession session = request.getSession(true);
        Object countWrong = session.getAttribute("countWrong");
        int count;
        if (countWrong == null) {
            count = 1;
        } else {
            count = (Integer) countWrong + 1;
        }
        session.setAttribute("countWrong", count);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        if (count > 2) {
            response.getWriter().write("{\"login\": false, \"reset_msg\":true}");
        } else {
            response.getWriter().write("{\"login\": false, \"reset_msg\":false}");
        }
    }
}