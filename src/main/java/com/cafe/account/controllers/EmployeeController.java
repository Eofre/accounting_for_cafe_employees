package com.cafe.account.controllers;

import com.cafe.account.dto.employee.EmployeeDto;
import com.cafe.account.dto.user.UserDto;
import com.cafe.account.models.Employee;
import com.cafe.account.models.User;
import com.cafe.account.repositories.EmployeeRepository;
import com.cafe.account.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public String employees(Model model) {
        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String employeesAdd(Model model) {
        model.addAttribute("employeeDto", new EmployeeDto());
        return "employee-add";
    }

    @PostMapping("/employees/add")
    public String employeesPostAdd(@ModelAttribute("employeeDto") EmployeeDto employeeDto,
                                   Model model) {
        try {
            Employee employee = employeeService.create(employeeDto);
            return "redirect:/"; // перенаправляем на главную страницу
        } catch (AuthenticationException ex) {
            model.addAttribute("error", "Invalid username or password");
            return "employee-add"; // возвращаем страницу входа и сообщение об ошибке
        }
    }
}
