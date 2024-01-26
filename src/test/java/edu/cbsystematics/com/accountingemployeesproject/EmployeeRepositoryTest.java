package edu.cbsystematics.com.accountingemployeesproject;

import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import edu.cbsystematics.com.accountingemployeesproject.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmployeeRepository employeeRepository;


    // Unit test for components DataSource, JdbcTemplate, EntityManager, and EmployeeRepository.
    @Test
    @DisplayName("=> JUnit Test for Spring Boot Components")
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(employeeRepository).isNotNull();

        System.out.println("DataSource is not null: " + dataSource);
        System.out.println("JdbcTemplate is not null: " + jdbcTemplate);
        System.out.println("EntityManager is not null: " + entityManager);
        System.out.println("EmployeeRepository is not null: " + employeeRepository);
    }

    // Creates a test employee
    private Employee createTestEmployee() {
        // Using the builder pattern
        return Employee.builder()
                .firstName("TestJpa")
                .lastName("TestJpa")
                .birthDate(LocalDate.of(1991, 8, 10))
                .email("test_jpa@example.com")
                .phoneNumber("+38097321112")
                .build();
    }

    @Test
    @Transactional
    @DisplayName("=> JUnit JpaTest for save employee operation")
    void givenEmployeeObject_whenSave() {
        // given - setup or precondition
        Employee employee = createTestEmployee();

        // when - action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - very output
        Assertions.assertNotNull(savedEmployee);
        assertThat(savedEmployee.getId()).isPositive();
        System.out.println("JpaTest givenEmployeeObject_whenSave(): Passed");
    }

    @Test
    @Transactional
    @DisplayName("=> JUnit JpaTest for get Employee by Id")
    void givenEmployeeId_whenFindByIdSavedEmployee() {
        // given - setup or precondition
        Employee employee = createTestEmployee();
        Employee savedEmployee = employeeRepository.save(employee);

        // when - when - action or the behavior that we are going to test
        Employee employeeDB = employeeRepository.findById(savedEmployee.getId()).orElse(null);

        // then - very output
        Assertions.assertNotNull(employeeDB);
        System.out.println("JpaTest givenEmployeeId_whenFindByIdSavedEmployee(): Passed");
    }

    @Test
    @Transactional
    @DisplayName("=> JUnit JpaTest for retrieving all employees from the repository")
    void givenEmployeesList_whenFindAll() {
        // given - precondition or setup
        long initialEmployeeCount = employeeRepository.count(); // Get the current number of employees

        Employee employee1 = createTestEmployee();
        Employee employee2 = createTestEmployee();

        // when - action or the behavior that we are going to test
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // Знову отримати кількість працівників після додавання нових
        long finalEmployeeCount = employeeRepository.count();

        // then - verify the output
        Assertions.assertEquals(initialEmployeeCount + 2, finalEmployeeCount); // +2
        System.out.println("JpaTest givenEmployeesList_whenFindAll: Passed");
    }

    @Test
    @Transactional
    @DisplayName("=> JUnit JpaTest for update employee operation")
    void givenEmployeeObject_whenUpdateEmployee() {
        // given - setup or precondition
        Employee employee = createTestEmployee();
        Employee savedEmployee = employeeRepository.save(employee);

        // when - when - action or the behavior that we are going to test
        savedEmployee.setFirstName("UpdateTest");
        savedEmployee.setEmail("update_test@example.com");

        employeeRepository.updateEmployee(
                savedEmployee.getId(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                savedEmployee.getBirthDate(),
                savedEmployee.getEmail(),
                savedEmployee.getPhoneNumber()
        );

        // Refresh
        Employee updatedEmployee = employeeRepository.findById(savedEmployee.getId()).orElse(null);

        // then - verify the output
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getFirstName()).isEqualTo("UpdateTest");
        assertThat(updatedEmployee.getEmail()).isEqualTo("update_test@example.com");
        System.out.println("JpaTest givenEmployeeObject_whenUpdateEmployee(): Passed");
    }

    @Test
    @Transactional
    @DisplayName("=> JUnit JpaTest for delete employee operation")
    void givenEmployeeObject_whenDelete() {
        // given - setup or precondition
        Employee employee = createTestEmployee();
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();
        System.out.println("JpaTest givenEmployeeObject_whenDelete: Passed");
    }

}




