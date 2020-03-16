package ru.spb.spring.libraryapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.spb.spring.libraryapp.domain.User;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest req) {
        model.addAttribute("user", new User());

        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req) {
        AuthenticationException authExcept = (AuthenticationException) req.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (authExcept != null) {
            String message = authExcept.getMessage();
            if (message.equals("Bad credentials")) {
                log.info("Login Error " + message + " uname: " + req.getParameter("j_username"));
                message = "Логин или пароль не подходят!";
            } else {
                log.error("Login Error " + message + " uname: " + req.getParameter("j_username"));
            }

            log.info(message);
        }

        return "library";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }

}
