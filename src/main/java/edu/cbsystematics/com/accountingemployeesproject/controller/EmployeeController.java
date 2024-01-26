package edu.cbsystematics.com.accountingemployeesproject.controller;

import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import edu.cbsystematics.com.accountingemployeesproject.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/add")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody @Valid Employee updatedEmployee) {
        return employeeService.findEmployeeById(id)
                .map(existingEmployee -> {
                    System.out.println("Found existing employee with ID: " + id);
                    // Set the ID of the updated employee
                    updatedEmployee.setId(id);
                    // Call the service method to update the employee
                    Employee updated = employeeService.updateEmployee(id, updatedEmployee);
                    System.out.println("Updated employee with ID: " + id);

                    return ResponseEntity.ok(updated); // Return 200 "OK" with the updated employee
                })
                .orElseGet(() -> {
                    System.out.println("Employee with ID " + id + " not found.");
                    return ResponseEntity.notFound().build(); // Return 404 "Not Found"
                });
    }

    @DeleteMapping("/delete/{id}")
    // Endpoint for deleting an employee by ID
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build(); // Return 204 "No Content"
    }

    @GetMapping("/{id}")
    // Endpoint for retrieving an employee by ID
    public ResponseEntity<?> findEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Additional endpoints for filtering employees

    @GetMapping("/department/{name}")
    // Endpoint for retrieving employees by department
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String name) {
        List<Employee> employees = employeeService.getEmployeeByDepartment(name);
        return handleEmployeeList(employees);
    }

    @GetMapping("/position/{name}")
    // Endpoint for retrieving employees by position
    public ResponseEntity<List<Employee>> getEmployeeByPosition(@PathVariable String name) {
        List<Employee> employees = employeeService.getEmployeeByPosition(name);
        return handleEmployeeList(employees);
    }

    @GetMapping("/age-range")
    // Endpoint for retrieving employees within a specified age range
    public ResponseEntity<List<Employee>> getEmployeesByAgeRange(@RequestParam("ageFrom") int ageFrom,
                                                                 @RequestParam("ageTo") int ageTo) {
        List<Employee> employees = employeeService.getEmployeesByAgeRange(ageFrom, ageTo);
        return handleEmployeeList(employees);
    }

    @GetMapping("/older-than")
    // Endpoint for retrieving employees older than a specified age
    public ResponseEntity<List<Employee>> findEmployeesOlderThan(@RequestParam("age") int age) {
        List<Employee> employees = employeeService.findEmployeesOlderThan(age);
        return handleEmployeeList(employees);
    }

    @GetMapping("/younger-than")
    // Endpoint for retrieving employees younger than a specified age
    public ResponseEntity<List<Employee>> findEmployeesYoungerThan(@RequestParam("age") int age) {
        List<Employee> employees = employeeService.findEmployeesYoungerThan(age);
        return handleEmployeeList(employees);
    }

    @GetMapping("/all")
    // Endpoint for retrieving all employees
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return handleEmployeeList(employees);
    }

    // Common method for handling the response
    public ResponseEntity<List<Employee>> handleEmployeeList(List<Employee> employees) {
        return employees != null && !employees.isEmpty()
                ? ResponseEntity.ok(employees)
                : ResponseEntity.notFound().build();
    }

}


