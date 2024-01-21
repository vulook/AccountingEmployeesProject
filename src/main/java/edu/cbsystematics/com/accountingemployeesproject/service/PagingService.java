package edu.cbsystematics.com.accountingemployeesproject.service;

import edu.cbsystematics.com.accountingemployeesproject.model.response.EmployeeResponse;
import org.springframework.data.domain.PageRequest;


public interface PagingService {

    // Retrieves a paginated list of employees
	EmployeeResponse findAllEmployees(PageRequest pageable);

}