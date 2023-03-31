package com.cafe.account.controllers;

import com.cafe.account.dto.employee.EmployeeDto;
import com.cafe.account.dto.position.PositionDto;
import com.cafe.account.models.Employee;
import com.cafe.account.models.Position;
import com.cafe.account.repositories.EmployeeRepository;
import com.cafe.account.service.EmployeeService;
import com.cafe.account.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PositionService positionService;


    @GetMapping("/employee/all")
    public String employees(Model model) {
        Iterable<Employee> employees = employeeRepository.findAll();
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
        try {
            employeeService.create(employeeDto);
            return "redirect:/employee/all";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "employee-add";
        }
    }
}
