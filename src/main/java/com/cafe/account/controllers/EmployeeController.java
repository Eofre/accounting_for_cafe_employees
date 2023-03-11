package com.cafe.account.controllers;

import com.cafe.account.models.Employee;
import com.cafe.account.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public String employees(Model model) {
        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String employeesAdd(Model model) {
        return "employee-add";
    }

    @PostMapping("/employees/add")
    public String employeesPostAdd(@RequestParam String fullName, @RequestParam String phoneNumber,
                                   @RequestParam String position, @RequestParam Integer hourlyRate,
                                   Model model) {
        Employee employee = new Employee(fullName, position,  hourlyRate, phoneNumber);
        employeeRepository.save(employee);
        return "redirect:/employees";
    }
}
