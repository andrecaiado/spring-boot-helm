package com.example.springboottemplate.service;

import com.example.springboottemplate.dto.CreateEmployeeDto;
import com.example.springboottemplate.dto.EmployeeDto;
import com.example.springboottemplate.entity.Employee;
import com.example.springboottemplate.exception.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.springboottemplate.repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> getAllEmployees(Pageable pageable){
        List<Employee> content = employeeRepository.findAll(pageable).getContent();
        return content.stream().map(EmployeeService::toEmployeeDto).toList();
    }

    private static EmployeeDto toEmployeeDto(Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getAge(), employee.getDesignation(), employee.getPhoneNumber(), employee.getJoinedOn(), employee.getAddress(), employee.getDateOfBirth(), employee.getCreatedAt(), employee.getUpdatedAt());
    }

    public EmployeeDto getEmployeeById(Integer id){
        Employee employeeById = findEmployeeById(id);
        if(employeeById != null){
            return toEmployeeDto(employeeById);
        }
        throw new EmployeeNotFoundException("Employee not found");
    }

    private Employee findEmployeeById(Integer id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        log.info("Employee with id: {} doesn't exist", id);
        return null;
    }

    public EmployeeDto saveEmployee (CreateEmployeeDto employeeDto){
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.firstName());
        employee.setLastName(employeeDto.lastName());
        employee.setAge(employeeDto.age());
        employee.setDesignation(employeeDto.designation());
        employee.setPhoneNumber(employeeDto.phoneNumber());
        employee.setJoinedOn(employeeDto.joinedOn());
        employee.setAddress(employeeDto.address());
        employee.setDateOfBirth(employeeDto.dateOfBirth());
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());

        Employee savedEmployee = employeeRepository.save(employee);

        log.info("Employee with id: {} saved successfully", employee.getId());
        return toEmployeeDto(savedEmployee);
    }

    public EmployeeDto updateEmployee (Integer id, CreateEmployeeDto employeeDto) {
        Employee employeeById = findEmployeeById(id);
        if(employeeById == null){
            throw new EmployeeNotFoundException("Employee not found");
        }

        employeeById.setFirstName(employeeDto.firstName());
        employeeById.setLastName(employeeDto.lastName());
        employeeById.setAge(employeeDto.age());
        employeeById.setDesignation(employeeDto.designation());
        employeeById.setPhoneNumber(employeeDto.phoneNumber());
        employeeById.setJoinedOn(employeeDto.joinedOn());
        employeeById.setAddress(employeeDto.address());
        employeeById.setDateOfBirth(employeeDto.dateOfBirth());
        employeeById.setUpdatedAt(LocalDateTime.now());

        employeeRepository.save(employeeById);

        log.info("Employee with id: {} updated successfully", id);
        return toEmployeeDto(employeeById);
    }

    public void deleteEmployeeById (Integer id) {
        Employee employeeById = findEmployeeById(id);
        if(employeeById == null){
            throw new EmployeeNotFoundException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

}
