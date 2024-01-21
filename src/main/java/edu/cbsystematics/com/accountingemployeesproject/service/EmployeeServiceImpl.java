package edu.cbsystematics.com.accountingemployeesproject.service;

import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import edu.cbsystematics.com.accountingemployeesproject.model.response.EmployeeResponse;
import edu.cbsystematics.com.accountingemployeesproject.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

@Transient
    @Override
    public void saveEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);

        // Log the creation of the employee
        String fullName = savedEmployee.getFirstName() + " " + savedEmployee.getLastName();
        logger.info("Employee: {} was created with ID: {}", fullName, savedEmployee.getId());
    }

    @Override
    public void updateEmployee(Long id, Employee updatedEmployee) {

        // Create an Employee object using the Builder
        Employee employeeToUpdate = Employee.builder()
                .firstName(updatedEmployee.getFirstName())
                .lastName(updatedEmployee.getLastName())
                .birthDate(updatedEmployee.getBirthDate())
                .email(updatedEmployee.getEmail())
                .phoneNumber(updatedEmployee.getPhoneNumber())
                .build();

        // Update the Employee using a query
        employeeRepository.updateEmployee(
                id,
                employeeToUpdate.getFirstName(),
                employeeToUpdate.getLastName(),
                employeeToUpdate.getBirthDate(),
                employeeToUpdate.getEmail(),
                employeeToUpdate.getPhoneNumber()
        );

        // Log the successful update
        String fullName = employeeToUpdate.getFirstName() + " " + employeeToUpdate.getLastName();
        logger.info("Profile updated for Employee: {} with ID: {}", fullName, id);

    }

    @Override
    public void deleteEmployee(Long id) {
        // Find the employee by ID
        Optional<Employee> employeeToDelete = employeeRepository.findById(id);
        if (employeeToDelete.isPresent()) {
            // Existing Employee found in the repository by ID.
            String fullName = employeeToDelete.get().getFirstName() + " " + employeeToDelete.get().getLastName();
            employeeRepository.deleteById(id);
            logger.info("Employee: {} was deleted with ID: {}", fullName, id);
        } else {
            // User with the specified ID isn't found.
            throw new EntityNotFoundException("Employee not found with ID: " + id);
        }
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }


    @Override
    public List<Employee> getEmployeeByDepartment(String name) {
        return employeeRepository.employeesByDepartment(name);
    }

    @Override
    public List<Employee> getEmployeeByPosition(String name) {
        return employeeRepository.employeesByPosition(name);
    }

    @Override
    public List<Employee> getEmployeesByAgeRange(int ageFrom, int ageTo) {
        // Determination of date of birth based on age
        LocalDate currentDate = LocalDate.now();
        LocalDate minDate = currentDate.minusYears(ageTo);
        LocalDate maxDate = currentDate.minusYears(ageFrom);

        return employeeRepository.findByAgeRange(minDate, maxDate);
    }

    @Override
    public List<Employee> findEmployeesOlderThan(int age) {
        // Determination of date of birth based on age
        LocalDate birthDateLimit = LocalDate.now().minusYears(age);
        return employeeRepository.findByBirthDateBefore(birthDateLimit);
    }

    @Override
    public List<Employee> findEmployeesYoungerThan(int age) {
        // Determination of date of birth based on age
        LocalDate birthDateLimit = LocalDate.now().minusYears(age);
        return employeeRepository.findByBirthDateLessThan(birthDateLimit);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    @Override
    public EmployeeResponse findAllEmployees(PageRequest pageable) {
        // Specify the type parameter for the employeesPage variable
        Page<Employee> employeesPage = employeeRepository.findAll(pageable);
        return buildResponse(employeesPage);
    }

    private EmployeeResponse buildResponse(Page<Employee> employeesPage) {
        // Use stream() to convert employeesPage to a List<Employee>
        List<Employee> employees = employeesPage.stream().toList();
        return EmployeeResponse.builder()
                .pageNumber(employeesPage.getNumber() + 1)
                .pageSize(employeesPage.getSize())
                .totalElements(employeesPage.getTotalElements())
                .totalPages(employeesPage.getTotalPages())
                .employees(employees)
                .isLastPage(employeesPage.isLast())
                .build();
    }

}