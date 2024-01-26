package edu.cbsystematics.com.accountingemployeesproject;

import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import edu.cbsystematics.com.accountingemployeesproject.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties") // Using H2
class EmployeeServiceAnotherTests {

    @Autowired
    private EmployeeService employeeService;

    // Employee object to be used in tests
    private Employee employeeTest;

    @BeforeEach
    public void setup() {
        // Initialization of the test employee
        employeeTest = Employee.builder()
                .firstName("TestService")
                .lastName("TestService")
                .birthDate(LocalDate.of(1995, 5, 5))
                .email("test_service@gmail.com")
                .phoneNumber("+38097321112")
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("It should save an employee to the database and find it by ID")
    void itShouldSaveAndFindEmployee() {
        // When
        Employee savedEmployee = employeeService.saveEmployee(employeeTest);

        // Then
        assertAll("Saved employee",
                () -> assertThat(savedEmployee.getId()).isPositive(),
                () -> assertThat(savedEmployee).isEqualTo(employeeTest));

        // Given
        Long employeeId = savedEmployee.getId();

        // When
        Optional<Employee> foundEmployeeOptional = employeeService.findEmployeeById(employeeId);

        // Then
        assertTrue(foundEmployeeOptional.isPresent(), "Employee not found");
        Employee foundEmployee = foundEmployeeOptional.orElseThrow();
        assertThat(foundEmployee)
                .usingRecursiveComparison()
                .ignoringFields("periods") // Ignore the 'periods' field
                .isEqualTo(savedEmployee);
    }

    @Test
    @Order(2)
    @DisplayName("It should find employees older than a given age")
    void itShouldFindEmployeesOlderThan() {
        // Given
        int age = 30; // Specify the age for testing

        // When
        List<Employee> olderEmployees = employeeService.findEmployeesOlderThan(age);

        System.out.println("List of birth dates and ages of employees older than " + age + ":");
        for (Employee emp : olderEmployees) {
            LocalDate birthDate = emp.getBirthDate();
            int calculatedAge = Period.between(birthDate, LocalDate.now()).getYears();
            System.out.println("Birth Date: " + birthDate + ", Age: " + calculatedAge);
        }

        // Then
        assertNotNull(olderEmployees);
        assertTrue(olderEmployees.stream().allMatch(emp ->
                emp.getBirthDate().isBefore(LocalDate.now().minusYears(age))));
    }

    @Test
    @Order(3)
    @DisplayName("It should find employees younger than a given age")
    void itShouldFindEmployeesYoungerThan() {
        // Given
        int age = 30; // Specify the age for testing

        // When
        List<Employee> youngerEmployees = employeeService.findEmployeesYoungerThan(age);

        System.out.println("List of birth dates and ages of employees younger than " + age + ":");
        for (Employee emp : youngerEmployees) {
            LocalDate birthDate = emp.getBirthDate();
            int calculatedAge = Period.between(birthDate, LocalDate.now()).getYears();
            System.out.println("Birth Date: " + birthDate + ", Age: " + calculatedAge);
        }

        // Then
        assertNotNull(youngerEmployees);
        assertTrue(youngerEmployees.stream().allMatch(emp ->
                emp.getBirthDate().isAfter(LocalDate.now().minusYears(age))));
    }

    @Test
    @Order(4)
    @DisplayName("It should find employees within the given age range")
    void itShouldFindEmployeesByAgeRange() {
        // Given
        int ageFrom = 25;
        int ageTo = 35;

        // When
        List<Employee> employeesInRange = employeeService.getEmployeesByAgeRange(ageFrom, ageTo);

        // Then
        assertNotNull(employeesInRange);
        assertFalse(employeesInRange.isEmpty(), "List of employees should not be empty");

        // Verify the employeesInRange
        System.out.println("List of birth dates and ages of employees from " + ageFrom + " to " +  ageTo + ":");
        assertThat(employeesInRange)
                .allSatisfy(employee -> {
                    LocalDate currentDate = LocalDate.now();
                    int age = Period.between(employee.getBirthDate(), currentDate).getYears();
                    assertThat(age)
                            .as("Employee age should be within the specified range")
                            .isGreaterThanOrEqualTo(ageFrom)
                            .isLessThanOrEqualTo(ageTo);
                    System.out.println("Birth Date: " + employee.getBirthDate() + ", Age: " + age);
                });


        //*********************************************************************************************************/
        // Illustrates various methods to verify the presence of the test employee in the employeesInRange
        Assertions.assertThat(employeesInRange).extracting(Employee::getFirstName).contains("TestService");

        Assertions.assertThat(employeesInRange)
                .extracting(Employee::getFirstName, Employee::getLastName, Employee::getBirthDate)
                .contains(tuple("TestService", "TestService", LocalDate.of(1995, 5, 5)));

        assertThat(employeesInRange, hasItem(allOf(
                hasProperty("firstName", is("TestService")),
                hasProperty("lastName", is("TestService")),
                hasProperty("birthDate", is(LocalDate.of(1995, 5, 5)))
        )));

    }

}

