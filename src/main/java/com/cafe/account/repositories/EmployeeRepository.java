package com.cafe.account.repositories;

import com.cafe.account.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByUserId(Long userId);
    Optional<Employee> findByIdAndUserId(Long id, Long userId);
}
