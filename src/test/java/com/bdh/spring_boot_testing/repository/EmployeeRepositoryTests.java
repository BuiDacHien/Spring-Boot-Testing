package com.bdh.spring_boot_testing.repository;

import com.bdh.spring_boot_testing.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @DisplayName("Unit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Bui")
                .lastName("Dac Hien")
                .email("hiendb123@gmail.com")
                .build();

        // when - action or behavior that we will test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("Unit test for get all employee list")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList() {
        // Given: setup data
        Employee employee1 = Employee.builder()
                .firstName("Bui")
                .lastName("Dac Hien 1")
                .email("hiendb1@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Bui")
                .lastName("Dac Hien 2")
                .email("hiendb1@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // when: action
        List<Employee> employeeList = employeeRepository.findAll();

        // then: verify output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Unit test for get employee by id")
    public void givenEmployee_whenFindById_thenEmployee() {
        // Given: setup data
        Employee employee1 = Employee.builder()
                .firstName("Bui")
                .lastName("Dac Hien 1")
                .email("hiendb1@gmail.com")
                .build();

        employeeRepository.save(employee1);

        // when: action
        Employee employee = employeeRepository.findById(employee1.getId()).get();

        // then: verify output
        assertThat(employee).isNotNull();
    }

    @Test
    @DisplayName("Unit test for get employee by email")
    public void givenEmployee_whenFindByEmail_thenReturnEmployeeObject() {
        // Given: setup data
        Employee employee1 = Employee.builder()
                .firstName("Bui")
                .lastName("Dac Hien 1")
                .email("hiendb1@gmail.com")
                .build();

        employeeRepository.save(employee1);

        // when: action
        Employee employee = employeeRepository.findByEmail(employee1.getEmail()).get();

        // then: verify output
        assertThat(employee).isNotNull();
    }
}
