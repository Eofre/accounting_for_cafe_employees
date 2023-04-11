package com.cafe.account.repositories;

import com.cafe.account.models.Employee;
import com.cafe.account.models.EmployeeWorkEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeWorkEntryRepository extends JpaRepository<EmployeeWorkEntry, Long> {

    List<EmployeeWorkEntry> findByEmployeeFullNameContaining(String fullName);
    List<EmployeeWorkEntry> findByEmployeeFullNameContainingAndDate(String fullName,LocalDate date);
    List<EmployeeWorkEntry> findAllByDate(LocalDate date);
    List<EmployeeWorkEntry> findAllByEmployee(Employee employee);
    List<EmployeeWorkEntry> findAllByDateAndEmployee(LocalDate date, Employee employee);
}
