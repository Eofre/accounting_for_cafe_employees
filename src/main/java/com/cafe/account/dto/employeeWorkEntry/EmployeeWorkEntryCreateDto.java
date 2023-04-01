package com.cafe.account.dto.employeeWorkEntry;

import com.cafe.account.models.Employee;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeWorkEntryCreateDto {
    private Long employeeId;

    private Double workedHours;

    private LocalDate date;
}
