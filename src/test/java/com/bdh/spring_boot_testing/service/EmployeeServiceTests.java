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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


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

    @DisplayName("Junit test for getAllEmployee method with positive context")
    @Test
    public void givenListEmployee_whenGetAll_thenReturnListEmployee() {
        // given
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Bui Dac 1")
                .lastName("Hien 1")
                .email("hiendb1@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // when: action we will test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then: verify output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("Junit test for getAllEmployee method with positive context")
    @Test
    public void givenListEmptyEmployee_whenGetAll_thenReturnListEmployee() {
        // given
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Bui Dac 1")
                .lastName("Hien 1")
                .email("hiendb1@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when: action we will test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then: verify output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    @DisplayName("Junit test for getEmployeeById method with prositive context")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        //given: precondition or setup data
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        //when: action we will test
        Employee savedEmployee =  employeeService.getEmployeeById(employee.getId()).get();

        //then: verify output
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("Junit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdate_thenReturnUpdatedEmployeeObject() {
        // given: precondition or setup data
        employee.setLastName("Hien 1");
        employee.setEmail("hiendb1@gmail.com");
        given(employeeRepository.save(employee)).willReturn(employee);

        // when: action we will test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then: verify output
        assertThat(updatedEmployee.getLastName()).isEqualTo("Hien 1");
        assertThat(updatedEmployee.getEmail()).isEqualTo("hiendb1@gmail.com");
    }

    @DisplayName("Junit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        // given: precondition or setup data
        willDoNothing().given(employeeRepository).deleteById(employee.getId());

        // when: action we will test
        employeeService.deleteEmployee(employee.getId());

        // then: verify output
        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }
}
