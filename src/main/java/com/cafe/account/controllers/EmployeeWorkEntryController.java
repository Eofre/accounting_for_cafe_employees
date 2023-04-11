package com.cafe.account.controllers;

import com.cafe.account.dto.employeeWorkEntry.EmployeeWorkEntryCreateDto;
import com.cafe.account.models.Employee;
import com.cafe.account.models.EmployeeWorkEntry;
import com.cafe.account.service.AuthService;
import com.cafe.account.service.EmployeeService;
import com.cafe.account.service.EmployeeWorkEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
public class EmployeeWorkEntryController {

    @Autowired
    private EmployeeWorkEntryService employeeWorkEntryService;

    @Autowired
    private AuthService authService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/timesheet/**")
    public String handleUnknownUrls() {
        return "redirect:/timesheet/entries/all";
    }

    @GetMapping("/timesheet/entries/all")
    public String entries(@RequestParam(required = false) String search,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                          Model model) {

        String template = "";

        List<String> roles = authService.getUserRoles();
        List<EmployeeWorkEntry> employeeWorkEntryList;

        if (roles.contains("ADMIN")) {

            if(search != null && date != null) {
                employeeWorkEntryList = employeeWorkEntryService.findByEmployeeFullNameContainingAndDate(search, date);
            }
            else if (search != null) {
                employeeWorkEntryList = employeeWorkEntryService.findByEmployeeFullNameContaining(search);
            }
            else if (date != null) {
                employeeWorkEntryList = employeeWorkEntryService.findAllByDate(date);
            }
            else {
                employeeWorkEntryList = employeeWorkEntryService.findAll();
            }
            template = "employee-work-entries";
        } else {
            Employee employee = authService.getEmployee();
            if (date != null) {
                employeeWorkEntryList = employeeWorkEntryService.findAllByDateAndEmployee(date, employee);
            } else {
                employeeWorkEntryList = employeeWorkEntryService.findByEmployee(employee);
            }
            template = "employee-work-entries-user";
        }

        model.addAttribute("entries", employeeWorkEntryList);
        return template;
    }

    @GetMapping("/timesheet/entries/add")
    public String entryAdd(Model model) {
        List<Employee> employeeList = employeeService.findAll();
        model.addAttribute("employeeList", employeeList);

        return "employee-work-entries-add";
    }

    @PostMapping("/timesheet/entries/add")
    public String entryPostAdd(@ModelAttribute("entry") EmployeeWorkEntryCreateDto employeeWorkEntryCreateDto, Model model) {
       try {
           employeeWorkEntryService.create(employeeWorkEntryCreateDto);
           return "redirect:/timesheet/entries/all";

       } catch (IllegalArgumentException e) {
           model.addAttribute("error", e.getMessage());
           return "employee-work-entries-add";
       }
    }

    @PostMapping("/timesheet/entries/{id}/remove")
    public String entryPostDelete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeWorkEntryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Запись успешно удаленна!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/timesheet/entries/all";
    }

}
