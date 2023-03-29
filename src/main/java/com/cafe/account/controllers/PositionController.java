package com.cafe.account.controllers;

import com.cafe.account.dto.position.PositionDto;
import com.cafe.account.dto.position.PositionUpdateDto;
import com.cafe.account.models.Position;
import com.cafe.account.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("/position/all")
    public String positions(@RequestParam(required = false) String search, Model model) {
        List<Position> positions = positionService.findAll();
        model.addAttribute("positions", positions);
        return "positions";
    }

    @GetMapping("/position/add")
    public String positionAdd(Model model) {
        model.addAttribute("positionDto", new PositionDto());
        return "position-add";
    }

    @GetMapping("/position/{id}/edit")
    public String positionEdit(@PathVariable("id") Long id, Model model) {
        PositionUpdateDto positionUpdateDto = positionService.getPositionById(id);
        model.addAttribute("position", positionUpdateDto);
        return "position-edit";
    }

    @PostMapping("/position/add")
    public String positionPostAdd(@ModelAttribute("positionDto") PositionDto positionDto,
                                   Model model) {
        try {
            positionService.create(positionDto);
            return "redirect:/position/all"; // перенаправляем на главную страницу
        } catch (AuthenticationException ex) {
            model.addAttribute("error", "Invalid username or password");
            return "position-add"; // возвращаем страницу входа и сообщение об ошибке
        }
    }

    @PostMapping("/position/{id}/remove")
    public String positionPostDelete(@PathVariable("id") Long id) {
        positionService.deleteById(id);
        return "redirect:/position/all";
    }

    @PostMapping("/position/{id}/edit")
    public String positionPostEdit(@PathVariable("id") Long id,
                                   @ModelAttribute("position") PositionUpdateDto positionUpdateDto,
                                   Model model) {
        try {
            positionService.updatePosition(positionUpdateDto);
            return "redirect:/position/all";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "position-edit";
        }

    }
}
