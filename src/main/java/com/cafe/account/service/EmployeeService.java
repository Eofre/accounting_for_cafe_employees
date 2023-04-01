package com.cafe.account.service;

import com.cafe.account.dto.employee.EmployeeDto;
import com.cafe.account.dto.employee.EmployeeUpdateDto;
import com.cafe.account.dto.position.PositionUpdateDto;
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

import java.util.List;
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

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

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

    public List<Employee> findByFullNameContaining(String fullName) {
        return employeeRepository.findByFullNameContaining(fullName);
    }

    public void deleteById(Long id) {
        Long idUser = employeeRepository.findById(id).orElseThrow().getUser().getId();
        employeeRepository.deleteById(id);
        userRepository.deleteById(idUser);
    }

    public Employee getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        employeeUpdateDto.setFullName(employee.getFullName());
        employeeUpdateDto.setPosition(employee.getPosition());
        employeeUpdateDto.setPhoneNumber(employee.getPhoneNumber());
        return employee;
    }

    public void updateEmployee(EmployeeUpdateDto employeeUpdateDto) {
        Employee employee = employeeRepository.findById(employeeUpdateDto.getId()).orElseThrow(
                () -> new EntityNotFoundException("Работник с id=" + employeeUpdateDto.getId() + " не найден"));

        if (employeeRepository.findByPhoneNumber(employeeUpdateDto.getPhoneNumber()).isPresent() && !employee.getPhoneNumber().equals(employeeUpdateDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Работник с номером " + employeeUpdateDto.getPhoneNumber() + " уже существует!");
        } else {
            employee.setFullName(employeeUpdateDto.getFullName());
            employee.setPhoneNumber(employeeUpdateDto.getPhoneNumber());
            employee.setPosition(employeeUpdateDto.getPosition());
            employeeRepository.save(employee);
        }
    }
}
