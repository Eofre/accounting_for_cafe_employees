package com.cafe.account.dto.employee;

import com.cafe.account.models.Position;
import lombok.Data;

@Data
public class EmployeeDto {
    private String fullName;
    private String phoneNumber;
    private Position position;
    private Double hourlyRate;
    private String username;
    private String password;
}
