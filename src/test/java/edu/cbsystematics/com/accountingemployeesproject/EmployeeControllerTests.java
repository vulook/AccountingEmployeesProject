package edu.cbsystematics.com.accountingemployeesproject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cbsystematics.com.accountingemployeesproject.controller.EmployeeController;
import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import edu.cbsystematics.com.accountingemployeesproject.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = EmployeeController.class)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    void init() {
        employee = Employee.builder()
                .id(1L)
                .firstName("TestController")
                .lastName("TestController")
                .birthDate(LocalDate.of(2000, 12, 12))
                .email("test_controller@example.com")
                .phoneNumber("+3809712121212")
                .build();
    }

    @Test
    @DisplayName("=> JUnit: Should create an employee and return its details")
    void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // Given
        given(employeeService.saveEmployee(any(Employee.class))).willReturn(employee);

        // When (HTTP POST request)
        ResultActions response = mockMvc.perform(post("/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // Display the status
        System.out.println("Status: " + response.andReturn().getResponse().getStatus());

        // Then the response status is CREATED
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.birthDate").value(employee.getBirthDate().toString()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(employee.getPhoneNumber()))
                .andDo(print());  // Printing the result of assertions

    }

    @Test
    @DisplayName("=> JUnit: Should find an employee by ID and return its details")
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // Given
        long employeeId = 1L;
        when(employeeService.findEmployeeById(employeeId)).thenReturn(Optional.of(employee));

        // When (HTTP GET request)
        ResultActions response = mockMvc.perform(get("/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON));

        // Display the status
        System.out.println("Status: " + response.andReturn().getResponse().getStatus());

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.birthDate").value(employee.getBirthDate().toString()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(employee.getPhoneNumber()))
                .andDo(print());  // Printing the result of assertions

    }

    @Test
    @DisplayName("=> JUnit: Should retrieve a list of all employees")
    void givenNoRequestParams_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
        // Given
        List<Employee> employees = Arrays.asList(employee, employee, employee);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employees/all")
                .contentType(MediaType.APPLICATION_JSON));

        // Display the status
        System.out.println("Status: " + response.andReturn().getResponse().getStatus());

        // Then
        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employees.size())))
                .andDo(print());  // Printing the result of assertions


        // ***Assert EmployeeList from response***
        MvcResult result = response.andReturn();
        String responseBody = result.getResponse().getContentAsString();

        // Deserialize the string into a List<Employee> object
        List<Employee> employeesList = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        // EmployeesList for assertions
        assertThat(employeesList)
                .hasSize(3)
                .containsExactlyElementsOf(employees);
    }

    @Test
    @DisplayName("=> JUnit: Should update an employee and return its updated details")
    void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {
        // Given
        long employeeId = 1L;
        Employee updatedEmployeeRequest = Employee.builder()
                .id(employeeId)
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .birthDate(LocalDate.of(1990, 11, 11))
                .email("updated_employee@example.com")
                .phoneNumber("+380971234567")
                .build();

        // Mocking the service behavior
        when(employeeService.findEmployeeById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeService.updateEmployee(employeeId, updatedEmployeeRequest)).thenReturn(updatedEmployeeRequest);

        // When (HTTP PUT request)
        ResultActions response = mockMvc.perform(put("/employees/update/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployeeRequest)));

        // Display the status
        System.out.println("Status: " + response.andReturn().getResponse().getStatus());

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value(updatedEmployeeRequest.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedEmployeeRequest.getLastName()))
                .andExpect(jsonPath("$.birthDate").value(updatedEmployeeRequest.getBirthDate().toString()))
                .andExpect(jsonPath("$.email").value(updatedEmployeeRequest.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(updatedEmployeeRequest.getPhoneNumber()))
                .andDo(print());
    }


    @Test
    @DisplayName("=> JUnit: Should delete an employee and return status 204")
    void givenEmployeeId_whenDeleteEmployee_thenReturn204() throws Exception {
        // Given
        long employeeId = 1L;

        // Mocking the service behavior
        when(employeeService.findEmployeeById(employeeId)).thenReturn(Optional.of(employee));
        doNothing().when(employeeService).deleteEmployee(employeeId);

        // When (HTTP DELETE request)
        ResultActions response = mockMvc.perform(delete("/employees/delete/{id}", employeeId));

        // Then
        response.andExpect(status().isNoContent())
                .andDo(print());

        // ***Assert status from response***
        int status = response.andReturn().getResponse().getStatus();
        assertEquals(204, status);

    }


}