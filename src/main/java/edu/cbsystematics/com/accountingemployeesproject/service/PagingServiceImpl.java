package edu.cbsystematics.com.accountingemployeesproject.service;

import edu.cbsystematics.com.accountingemployeesproject.config.PaginationConstants;
import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import edu.cbsystematics.com.accountingemployeesproject.repository.EmployeeRepository;
import edu.cbsystematics.com.accountingemployeesproject.model.response.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PagingServiceImpl implements PagingService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public PagingServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeResponse findAllEmployees(PageRequest pageable) {
        // Validate the input pageable parameter
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable cannot be null");
        }

        // Retrieve a page of employees from the repository.
        Page<Employee> employeesPage = employeeRepository.findAll(pageable);

        // Form and return the response.
        return buildResponse(employeesPage);
    }

    private EmployeeResponse buildResponse(Page<Employee> employeesPage) {
        List<Employee> employees = employeesPage.getContent();
        String sortField = PaginationConstants.DEFAULT_SORT;
        String sortDirection = PaginationConstants.DEFAULT_SORT_DIRECTION;

        // Build response.
        return EmployeeResponse.builder()
                .employees(employees)
                .pageNumber(employeesPage.getNumber() + 1)
                .pageSize(employeesPage.getSize())
                .totalElements(employeesPage.getTotalElements())
                .totalPages(employeesPage.getTotalPages())
                .isLastPage(employeesPage.isLast())
                .sortField(sortField)
                .sortDirection(sortDirection)
                .build();
    }

}