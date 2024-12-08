package com.bdh.spring_boot_testing.service;

import com.bdh.spring_boot_testing.exception.ResourceNotFoundException;
import com.bdh.spring_boot_testing.model.Employee;
import com.bdh.spring_boot_testing.repository.EmployeeRepository;
import com.bdh.spring_boot_testing.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Bui Dac")
                .lastName("Hien")
                .email("hiendb@gmail.com")
                .build();
    }

    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnEmployee() {
        // given: precondition or setup data
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // when
        Employee saveEmployee = employeeService.saveEmployee(employee);

        // then
        assertThat(saveEmployee).isNotNull();
    }

    @DisplayName("JUnit test for saveEmployee method when occur exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given: precondition or setup data
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // when: action that we will test
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then: verify output
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}
