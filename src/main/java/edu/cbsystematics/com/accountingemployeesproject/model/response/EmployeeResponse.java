package edu.cbsystematics.com.accountingemployeesproject.model.response;

import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import lombok.*;

import java.util.List;


@Builder
public record EmployeeResponse(List<Employee> employees, int pageNumber, int pageSize, long totalElements,
							   int totalPages, boolean isLastPage, String sortField, String sortDirection) {

}