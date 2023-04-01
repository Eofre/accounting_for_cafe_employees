package com.cafe.account.service;

import com.cafe.account.dto.employee.EmployeeUpdateDto;
import com.cafe.account.dto.employeeWorkEntry.EmployeeWorkEntryCreateDto;
import com.cafe.account.models.Employee;
import com.cafe.account.models.EmployeeWorkEntry;
import com.cafe.account.repositories.EmployeeWorkEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeWorkEntryService {

    @Autowired
    private EmployeeWorkEntryRepository employeeWorkEntryRepository;

    @Autowired
    private EmployeeService employeeService;

    public List<EmployeeWorkEntry> findAll() {
        return employeeWorkEntryRepository.findAll();
    }

    public EmployeeWorkEntry create(EmployeeWorkEntryCreateDto employeeWorkEntryCreateDto) {

        EmployeeWorkEntry employeeWorkEntry = new EmployeeWorkEntry();

        Employee employee = employeeService.getEmployeeById(employeeWorkEntryCreateDto.getEmployeeId());
        double hourlyRate = employee.getPosition().getHourlyRate();

        employeeWorkEntry.setEmployee(employee);
        employeeWorkEntry.setWorkedHours(employeeWorkEntryCreateDto.getWorkedHours());
        employeeWorkEntry.setEarnings(employeeWorkEntryCreateDto.getWorkedHours() * hourlyRate);
        employeeWorkEntry.setDate(employeeWorkEntryCreateDto.getDate());
        return employeeWorkEntryRepository.save(employeeWorkEntry);
    }

    public List<EmployeeWorkEntry> findByEmployeeFullNameContaining(String fullName) {
        return employeeWorkEntryRepository.findByEmployeeFullNameContaining(fullName);
    }

    public List<EmployeeWorkEntry> findAllByDate(LocalDate date) {
        return employeeWorkEntryRepository.findAllByDate(date);
    }

    public void deleteById(Long id) {
        employeeWorkEntryRepository.deleteById(id);
    }
}
