package com.cafe.account.controllers;

import com.cafe.account.dto.user.UserDto;
import com.cafe.account.models.User;
import com.cafe.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@ModelAttribute("userDto") UserDto userDto, Model model) {
        try {
            User user = userService.authenticate(userDto);
            // здесь вы можете добавить атрибуты в сессию, чтобы помнить, что пользователь вошел в систему
            return "redirect:/"; // перенаправляем на главную страницу
        } catch (AuthenticationException ex) {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // возвращаем страницу входа и сообщение об ошибке
        }
    }

    @GetMapping("/registration")
    public String getRegisterPage(Model model) {
      model.addAttribute("registrationDto", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("registrationDto") @Validated UserDto registrationDto, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }
        userService.create(registrationDto);
        return "redirect:/login";
    }
}
