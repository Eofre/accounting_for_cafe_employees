package com.cafe.account.dto.employee;

import com.cafe.account.models.Position;
import lombok.Data;

@Data
public class EmployeeUpdateDto {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private Position position;
}
