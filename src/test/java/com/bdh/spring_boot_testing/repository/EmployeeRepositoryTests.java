package com.bdh.spring_boot_testing.repository;

import com.bdh.spring_boot_testing.model.Employee;
import org.junit.jupiter.api.BeforeEach;
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

    private Employee employee1;

    @BeforeEach
    public void setup() {
        employee1 = Employee.builder()
                .firstName("Bui")
                .lastName("Dac Hien 1")
                .email("hiendb1@gmail.com")
                .build();
    }

    @DisplayName("Unit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given - precondition or setup
//        Employee employee1 = Employee.builder()
//                .firstName("Bui")
//                .lastName("Dac Hien 1")
//                .email("hiendb123@gmail.com")
//                .build();

        // when - action or behavior that we will test
        Employee savedEmployee = employeeRepository.save(employee1);

        // then - verify output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("Unit test for get all employee list")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList() {
        // Given: setup data
//        Employee employee1 = Employee.builder()
//                .firstName("Bui")
//                .lastName("Dac Hien 1")
//                .email("hiendb1@gmail.com")
//                .build();
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
//        Employee employee1 = Employee.builder()
//                .firstName("Bui")
//                .lastName("Dac Hien 1")
//                .email("hiendb1@gmail.com")
//                .build();

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
//        Employee employee1 = Employee.builder()
//                .firstName("Bui")
//                .lastName("Dac Hien 1")
//                .email("hiendb1@gmail.com")
//                .build();

        employeeRepository.save(employee1);

        // when: action
        Employee employee = employeeRepository.findByEmail(employee1.getEmail()).get();

        // then: verify output
        assertThat(employee).isNotNull();
    }

    @Test
    @DisplayName("Unit test for update employee")
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() {
        // Given: setup data
//        Employee employee1 = Employee.builder()
//                .firstName("Bui")
//                .lastName("Dac Hien 1")
//                .email("hiendb1@gmail.com")
//                .build();

        employeeRepository.save(employee1);

        // when: action
        Employee employee = employeeRepository.findById(employee1.getId()).get();
        employee.setFirstName("Bui 1");
        employee.setLastName("Dac Hien 2");
        employee.setEmail("hiendb2@gmail.com");
        Employee updateEmployee = employeeRepository.save(employee);

        // then: verify output
        assertThat(updateEmployee.getEmail()).isEqualTo("hiendb2@gmail.com");
        assertThat(updateEmployee.getFirstName()).isEqualTo("Bui 1");
        assertThat(updateEmployee.getLastName()).isEqualTo("Dac Hien 2");
    }

    @Test
    @DisplayName("Unit test for delete employee")
    public void givenEmployee_whenDeleteEmployee_thenReturnDeleteEmployeeObject() {
        // Given: setup data
//        Employee employee1 = Employee.builder()
//                .firstName("Bui")
//                .lastName("Dac Hien 1")
//                .email("hiendb1@gmail.com")
//                .build();

        employeeRepository.save(employee1);

        // when: action
        Employee savedEmployee = employeeRepository.findById(employee1.getId()).get();
        employeeRepository.deleteById(savedEmployee.getId());
        Optional<Employee> employeeOptional =  employeeRepository.findById(savedEmployee.getId());

        // then: verify output
        assertThat(employeeOptional).isEmpty();
    }

    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenfindByJPQL_thenReturnEmployeeObject() {
        // Given: Create data and store DB H2
//        Employee employee1 = Employee.builder()
//                .firstName("Bui")
//                .lastName("Dac Hien 1")
//                .email("hiendb1@gmail.com")
//                .build();

        employeeRepository.save(employee1);
        String firstName = "Bui";
        String lastName = "Dac Hien 1";

        // when: action -> find employee by findByJPQL function
        Employee employee = employeeRepository.findByJPQL(firstName, lastName);

        // then: verify output
        assertThat(employee).isNotNull();
    }

    @DisplayName("JUnit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
        // Given: Create data and store DB H2
//        Employee employee1 = Employee.builder()
//                .firstName("Bui")
//                .lastName("Dac Hien 1")
//                .email("hiendb1@gmail.com")
//                .build();

        employeeRepository.save(employee1);
        String firstName = "Bui";
        String lastName = "Dac Hien 1";

        // when: action -> find employee by findByJPQL function
        Employee employee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        // then: verify output
        assertThat(employee).isNotNull();
    }

    // JUnit test for custom query using native SQL with index
    @DisplayName("JUnit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){
        // given - precondition or setup
//        Employee employee1 = Employee.builder()
//                .firstName("Bui")
//                .lastName("Dac Hien 1")
//                .email("hiendb1@gmail.com")
//                .build();
        employeeRepository.save(employee1);
        // String firstName = "Ramesh";
        // String lastName = "Fadatare";

        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee1.getFirstName(), employee1.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for custom query using native SQL with named params
    @DisplayName("JUnit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){
        // given - precondition or setup
//        Employee employee1 = Employee.builder()
//                .firstName("Bui")
//                .lastName("Dac Hien 1")
//                .email("hiendb1@gmail.com")
//                .build();
        employeeRepository.save(employee1);
        // String firstName = "Ramesh";
        // String lastName = "Fadatare";

        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee1.getFirstName(), employee1.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }
}
