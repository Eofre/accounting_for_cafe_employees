package com.cafe.account.dto.position;

import lombok.Data;

@Data
public class PositionUpdateDto {
    private Long id;
    private String name;
    private Double hourlyRate;
}
