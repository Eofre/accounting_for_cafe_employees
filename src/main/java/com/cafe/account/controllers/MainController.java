package com.cafe.account.controllers;

import com.cafe.account.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class MainController {

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String home() {
        List<String> roles = authService.getUserRoles();

        if (roles.contains("ADMIN")) {
            return "home";
        } else  {
            return "home-user";
         }
    }

}
