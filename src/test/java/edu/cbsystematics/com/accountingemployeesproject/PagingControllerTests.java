package edu.cbsystematics.com.accountingemployeesproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cbsystematics.com.accountingemployeesproject.controller.PagingController;
import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import edu.cbsystematics.com.accountingemployeesproject.model.response.EmployeeResponse;
import edu.cbsystematics.com.accountingemployeesproject.service.PagingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(controllers = PagingController.class)
@ExtendWith(MockitoExtension.class)
class PagingControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PagingService pagingService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee1;
    private Employee employee2;
    private Employee employee3;

    @BeforeEach
    void init() {
        // Employee 1
        employee1 = Employee.builder()
                .id(1L)
                .firstName("TestPaging1")
                .lastName("TestPaging1")
                .birthDate(LocalDate.of(2001, 11, 11))
                .email("test_paging1@example.com")
                .phoneNumber("+380977777771")
                .build();

        // Employee 2
        employee2 = Employee.builder()
                .id(2L)
                .firstName("TestPaging2")
                .lastName("TestPaging2")
                .birthDate(LocalDate.of(2002, 12, 12))
                .email("test_paging2@example.com")
                .phoneNumber("+380977777772")
                .build();

        // Employee 3
        employee3 = Employee.builder()
                .id(3L)
                .firstName("TestPaging3")
                .lastName("TestPaging3")
                .birthDate(LocalDate.of(2003, 1, 1))
                .email("test_paging3@example.com")
                .phoneNumber("+380977777773")
                .build();
    }

    @Test
    @DisplayName("Should fetch employees with pagination and sorting")
    void fetchPagedEmployees_givenSortFieldAndSortDirection_returnsPagedResult() throws Exception {

        // Given

        // Constants
        final int pageNumber = 0;
        final int pageSize = 3;
        final String sortField = "id";
        final String sortDirection = "DESC";

        // Expected employees
        List<Employee> expectedEmployees = Arrays.asList(
                employee1,
                employee2,
                employee3
        );

        // Create a PageRequest (page number, page size, sort direction, sort field)
        PageRequest pageRequest = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.Direction.fromString(sortDirection),
                sortField
        );

        // Create a simulated Page of employees
        Page<Employee> employeesPage = new PageImpl<>(
                expectedEmployees,
                pageRequest,
                expectedEmployees.size());

        // Mocking the service behavior
        when(pagingService.findAllEmployees(any(PageRequest.class)))
                .thenAnswer(invocation -> {
                    // Return the response
                    return EmployeeResponse.builder()
                            .employees(employeesPage.getContent())
                            .pageNumber(employeesPage.getNumber() + 1)
                            .pageSize(employeesPage.getSize())
                            .totalElements(employeesPage.getTotalElements())
                            .totalPages(employeesPage.getTotalPages())
                            .isLastPage(employeesPage.isLast())
                            .sortField(employeesPage.getSort().iterator().next().getProperty())
                            .sortDirection(employeesPage.getSort().iterator().next().isAscending() ? "ASC" : "DESC")
                            .sortDirection(
                                    employeesPage.getSort().stream()
                                            .filter(order -> sortField.equals(order.getProperty()))
                                            .findFirst()
                                            .map(order -> order.getDirection().name())
                                            .orElse(Sort.DEFAULT_DIRECTION.name())
                            )
                            .build();
                });

        // When (HTTP GET request)
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/pageable/all")
                        .param("pageNumber", String.valueOf(pageNumber))  // Page number
                        .param("pageSize", String.valueOf(pageSize))      // Page size
                        .param("sortField", sortField)                    // Sort field
                        .param("sortDirection", sortDirection)            // Sort direction
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("Response content: " + content);

        EmployeeResponse actualResponse = objectMapper.readValue(content, EmployeeResponse.class);

        // Assertions
        assertThat(actualResponse.employees()).isEqualTo(expectedEmployees);
        assertThat(actualResponse.pageNumber()).isEqualTo(1);
        assertThat(actualResponse.pageSize()).isEqualTo(pageSize);
        assertThat(actualResponse.totalElements()).isEqualTo(expectedEmployees.size());
        assertThat(actualResponse.totalPages()).isEqualTo(1);
        assertThat(actualResponse.isLastPage()).isTrue();
        assertThat(actualResponse.sortField()).isEqualTo(sortField);
        assertThat(actualResponse.sortDirection()).isEqualTo(sortDirection);
    }



}