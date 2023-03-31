package com.cafe.account.service;

import com.cafe.account.dto.employee.EmployeeDto;
import com.cafe.account.models.Employee;
import com.cafe.account.models.Position;
import com.cafe.account.models.Role;
import com.cafe.account.models.User;
import com.cafe.account.repositories.EmployeeRepository;
import com.cafe.account.repositories.PositionRepository;
import com.cafe.account.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UserRepository userRepository;

    public void create (EmployeeDto employeeDto) {
        String username = employeeDto.getUsername();
        String phoneNumber = employeeDto.getPhoneNumber();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Пользователь с логином: " + username + " уже существует");
        }
        else if (employeeRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new IllegalArgumentException("Работник с телефоном: " + phoneNumber + " уже существует");
        } else {
            Employee employee = new Employee();
            employee.setFullName(employeeDto.getFullName());
            employee.setPhoneNumber(employeeDto.getPhoneNumber());

            Position position = positionRepository.findById(employeeDto.getPositionId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid position id: " + employeeDto.getPositionId()));
            employee.setPosition(position);

            User user = new User();
            user.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
            user.setUsername(employeeDto.getUsername());
            user.setRole(Role.USER);
            userRepository.save(user);
            employee.setUser(user);

            employeeRepository.save(employee);
        }
    }
}
