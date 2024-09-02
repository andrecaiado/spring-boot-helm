package com.example.springboottemplate;

import com.example.springboottemplate.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringBootTemplateApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmployeeService employeeService;

	@Test
	void givenInitialDb_whenGetAllEmployees_shouldReturn20() {
		assertEquals(20, employeeService.getAllEmployees(Pageable.ofSize(20)).size());
	}

}
