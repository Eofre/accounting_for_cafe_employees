package com.cafe.account.controllers;

import com.cafe.account.dto.employee.EmployeeDto;
import com.cafe.account.dto.employee.EmployeeUpdateDto;
import com.cafe.account.dto.position.PositionDto;
import com.cafe.account.dto.position.PositionUpdateDto;
import com.cafe.account.models.Employee;
import com.cafe.account.models.Position;
import com.cafe.account.repositories.EmployeeRepository;
import com.cafe.account.service.EmployeeService;
import com.cafe.account.service.PositionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PositionService positionService;

    @GetMapping("/employee/**")
    public String handleUnknownUrls() {
        return "redirect:/employee/all";
    }

    @GetMapping("/employee/all")
    public String employees(@RequestParam(required = false) String search, Model model) {
        List<Employee> employees;

        if (search != null) {
            employees = employeeService.findByFullNameContaining(search);
        } else {
            employees = employeeService.findAll();
        }

        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employee/add")
    public String employeesAdd(Model model) {
        List<Position> positions = positionService.findAll();
        model.addAttribute("employeeDto", new EmployeeDto());
        model.addAttribute("positions",positions);
        return "employee-add";
    }

    @PostMapping("/employee/add")
    public String employeesPostAdd(@ModelAttribute("employeeDto") EmployeeDto employeeDto,
                                   Model model) {
        List<Position> positions = positionService.findAll();
        try {
            employeeService.create(employeeDto);
            return "redirect:/employee/all";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("positions",positions);
            return "employee-add";
        }
    }

    @PostMapping("/employee/{id}/remove")
    public String employeePostDelete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Сотрудник успешно удален!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/employee/all";
    }

    @GetMapping("/employee/{id}/edit")
    public String employeeEdit(@PathVariable("id") Long id, Model model) {
        List<Position> positions = positionService.findAll();
        try {
            Employee employee = employeeService.getEmployeeById(id);
            model.addAttribute("employee", employee);
            model.addAttribute("positions", positions);
            return "employee-edit";
        } catch (EntityNotFoundException e) {
            return "redirect:/employee/all";
        }

    }

    @PostMapping("/employee/{id}/edit")
    public String employeePostEdit(@PathVariable("id") Long id,
                                   @ModelAttribute("employee") EmployeeUpdateDto employeeUpdateDto,
                                   Model model) {
        List<Position> positions = positionService.findAll();
        try {
            employeeService.updateEmployee(employeeUpdateDto);
            return "redirect:/employee/all";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("positions", positions);
            return "employee-edit";
        }
    }
}
