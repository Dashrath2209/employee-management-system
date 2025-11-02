package com.jayaa.ems.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeeDto {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 100, message = "Department name too long")
    private String department;
}