package com.cafe.account.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String employees(Model model) {
        model.addAttribute("title", "Сотрудники");
        return "home";
    }

}
