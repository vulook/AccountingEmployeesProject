package edu.cbsystematics.com.accountingemployeesproject.service;

import edu.cbsystematics.com.accountingemployeesproject.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.function.IntPredicate;


public interface EmployeeService {

	// Saves a new employee
	Employee saveEmployee(Employee employee);

	// Updates an existing employee by ID
	void updateEmployee(Long id, Employee updatedEmployee);

	// Retrieves an employee by their ID
	Optional<Employee> findEmployeeById(Long id);

	// Deletes an employee by their ID
	void deleteEmployee(Long id);

	// Retrieves a list of employees based on the department name
	List<Employee> getEmployeeByDepartment(String name);

	// Retrieves a list of employees based on the position name
	List<Employee> getEmployeeByPosition(String name);

	// Retrieves a list of employees within a specified age range
	List<Employee> getEmployeesByAgeRange(int ageFrom, int ageTo);

	// Retrieves a list of employees older than a specified age
	List<Employee> findEmployeesOlderThan(int age);

	// Retrieves a list of employees younger than a specified age
	List<Employee> findEmployeesYoungerThan(int age);

    List<Employee> getAllEmployees();

}