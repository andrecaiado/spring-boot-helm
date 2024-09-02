package com.example.springboottemplate.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmployeeDto(Integer id, String firstName, String lastName, Integer age, String designation, String phoneNumber, LocalDate joinedOn, String address, LocalDate dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
