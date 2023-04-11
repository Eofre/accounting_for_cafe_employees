package com.cafe.account.controllers;

import com.cafe.account.dto.position.PositionDto;
import com.cafe.account.dto.position.PositionUpdateDto;
import com.cafe.account.models.Position;
import com.cafe.account.service.PositionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("/position/**")
    public String handleUnknownUrls() {
        return "redirect:/position/all";
    }

    @GetMapping("/position/all")
    public String positions(@RequestParam(required = false) String search, Model model) {
        List<Position> positions;

        if (search != null) {
            positions = positionService.findByNameContaining(search);
        } else {
            positions = positionService.findAll();
        }
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
        try {
            PositionUpdateDto positionUpdateDto = positionService.getPositionById(id);
            model.addAttribute("position", positionUpdateDto);
            return "position-edit";
        } catch (EntityNotFoundException e) {
            return "redirect:/position/all";
        }

    }

    @PostMapping("/position/add")
    public String positionPostAdd(@ModelAttribute("positionDto") PositionDto positionDto,
                                   Model model) {
        try {
            positionService.create(positionDto);
            model.addAttribute("success", "Должность успешно добавлена!");
            return "position-add"; // перенаправляем на главную страницу
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "position-add";
        }
    }

    @PostMapping("/position/{id}/remove")
    public String positionPostDelete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            positionService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Должность успешно удаленна!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
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
