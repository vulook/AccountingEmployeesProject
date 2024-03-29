package edu.cbsystematics.com.accountingemployeesproject;

import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import edu.cbsystematics.com.accountingemployeesproject.repository.EmployeeRepository;
import edu.cbsystematics.com.accountingemployeesproject.service.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .id(1L)
                .firstName("TestService")
                .lastName("TestService")
                .birthDate(LocalDate.of(1991, 1, 1))
                .email("test_service@example.com")
                .phoneNumber("+380971111111")
                .build();
    }

    @Test
    @DisplayName("=> JUnit test for saveEmployee method with MockitoExtension")
    void givenEmployeeObject_whenSaveEmployee() {
        // given
        given(employeeRepository.save(any(Employee.class))).willReturn(employee);

        //when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        // when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // Using AssertJ to check the correctness of the expected values.
        Assertions.assertThat(savedEmployee).isNotNull();
        verify(employeeRepository, times(1)).save(any());

        // Output the result to console
        System.out.println("Test givenEmployeeObject_whenSaveEmployee() with MockitoExtension: Passed");
        System.out.println("Saved Employee: " + savedEmployee);
    }

    @Test
    @DisplayName("=> JUnit test for get Employee by Id with MockitoExtension")
    void givenEmployeeId_whenFindByIdSavedEmployee() {
        // given
        long employeeId = 1L;
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

        // when
        Optional<Employee> foundEmployee = employeeService.findEmployeeById(employeeId);

        // then
        assertThat(foundEmployee).isEqualTo(Optional.of(employee));

        // Output the result to console
        System.out.println("Test givenEmployeeId_whenFindByIdSavedEmployee() with MockitoExtension: Passed");
        System.out.println("Found Employee: " + foundEmployee.orElse(null));
    }

    @Test
    @DisplayName("=> JUnit test for getAllEmployees method with MockitoExtension")
    void givenEmployeesList_whenGetAllEmployees() {
        // given
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee));

        // when
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then
        // Using AssertJ to check the correctness of the expected values.
        Assertions.assertThat(employeeList)
                .isNotNull()
                .hasSize(2)
                .contains(employee, employee);

        // Output the result to console
        System.out.println("Test givenEmployeesList_whenGetAllEmployees() with MockitoExtension : Passed");
        System.out.println("Size of the Employee List: " + employeeList.size());
    }

    @Test
    @DisplayName("=> JUnit test for updateEmployee method with MockitoExtension")
    void givenEmployeeObject_whenUpdateEmployee() {
        // given
        long employeeId = 1L;
        Employee updatedEmployeeRequest = Employee.builder()
                .firstName("TestService2")
                .lastName("TestService2")
                .birthDate(LocalDate.of(1992, 12, 12))
                .email("test_service2@example.com")
                .phoneNumber("+380971211212")
                .build();

        // when
        employeeService.updateEmployee(employeeId, updatedEmployeeRequest);

        // then
        verify(employeeRepository).updateEmployee(
                1L,
                "TestService2",
                "TestService2",
                LocalDate.of(1992, 12, 12),
                "test_service2@example.com",
                "+380971211212"
        );

        // Output the result to console
        System.out.println("Test givenEmployeeObject_whenUpdateEmployee() with MockitoExtension : Passed");
    }

    @Test
    @DisplayName("=> JUnit test for deleteEmployee method with MockitoExtension")
    void givenEmployeeId_whenDeleteEmployee() {
        // given
        long employeeId = 1L;
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when
        employeeService.deleteEmployee(employeeId);

        // then
        verify(employeeRepository, times(1)).deleteById(employeeId);

        // Output the result to console
        System.out.println("Test givenEmployeeId_whenDeleteEmployee() with MockitoExtension : Passed");
        System.out.println("Employee with ID " + employeeId + " deleted successfully.");
    }

}