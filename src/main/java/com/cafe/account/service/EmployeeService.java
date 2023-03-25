package com.cafe.account.service;

import com.cafe.account.dto.employee.EmployeeDto;
import com.cafe.account.models.Employee;
import com.cafe.account.models.Role;
import com.cafe.account.models.User;
import com.cafe.account.repositories.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;

    public Employee create (EmployeeDto employeeDto) {

        Employee employee = new Employee();
        employee.setFullName(employeeDto.getFullName());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setPosition(employeeDto.getPosition());

        User user = new User();
        user.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        user.setUsername(employeeDto.getUsername());
        user.setRole(Role.USER);
        employee.setUser(user);

        return employeeRepository.save(employee);
    }

}
