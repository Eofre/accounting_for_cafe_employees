package com.cafe.account.controllers;

import com.cafe.account.models.Employee;
import com.cafe.account.models.User;
import com.cafe.account.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import org.springframework.ui.Model;


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
            Employee employee = authService.getEmployee();
            model.addAttribute("employee",employee);
            return "home-user";
         }
    }

}
