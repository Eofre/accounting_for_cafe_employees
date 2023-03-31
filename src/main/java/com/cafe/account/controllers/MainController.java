package com.cafe.account.controllers;

import com.cafe.account.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class MainController {

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String home(Model model) {
        List<String> roles = authService.getUserRoles();

        if (roles.contains("ADMIN")) {
            return "home";
        } else  {
            return "home-user";
         }
    }

}
