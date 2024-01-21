package edu.cbsystematics.com.accountingemployeesproject.service;

import edu.cbsystematics.com.accountingemployeesproject.model.Department;

import java.util.List;

public interface DepartmentService {

	// Save a department
	void saveDepartment(Department department);

	// Delete a department by ID
	void deleteDepartment(Long id);

	// Get all departments
	List<Department> getAllDepartments();

}