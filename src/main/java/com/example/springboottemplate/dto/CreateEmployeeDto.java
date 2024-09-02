package com.example.springboottemplate.dto;

import java.time.LocalDate;

public record CreateEmployeeDto(String firstName, String lastName, Integer age, String designation, String phoneNumber, LocalDate joinedOn, String address, LocalDate dateOfBirth) {
}
