package edu.cbsystematics.com.accountingemployeesproject.service;

import edu.cbsystematics.com.accountingemployeesproject.model.Department;
import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import edu.cbsystematics.com.accountingemployeesproject.model.Period;
import edu.cbsystematics.com.accountingemployeesproject.model.Position;

import edu.cbsystematics.com.accountingemployeesproject.model.request.PeriodCreateRequest;
import edu.cbsystematics.com.accountingemployeesproject.model.request.PeriodUpdateRequest;
import edu.cbsystematics.com.accountingemployeesproject.model.response.PeriodResponse;
import edu.cbsystematics.com.accountingemployeesproject.repository.DepartmentRepository;
import edu.cbsystematics.com.accountingemployeesproject.repository.EmployeeRepository;
import edu.cbsystematics.com.accountingemployeesproject.repository.PeriodRepository;
import edu.cbsystematics.com.accountingemployeesproject.repository.PositionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodServiceImpl implements PeriodService {

    private static final Logger logger = LoggerFactory.getLogger(PeriodServiceImpl.class);

    private final PeriodRepository periodRepository;

    private final DepartmentRepository departmentRepository;

    private final PositionRepository positionRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public PeriodServiceImpl(PeriodRepository periodRepository, DepartmentRepository departmentRepository, PositionRepository positionRepository, EmployeeRepository employeeRepository) {
        this.periodRepository = periodRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @Override
    public void createPeriod(PeriodCreateRequest request) {
        // Find the employee by ID
        Employee existingEmployee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + request.getEmployeeId()));

        // Check if the last period is closed
        if (!isLastPeriodClosed(existingEmployee)) {
            logger.error("The employee {} still has an active period", existingEmployee.getFirstName() + " " + existingEmployee.getLastName());
            throw new IllegalStateException("Cannot add a new period while the last one is still active.");
        }

        // Find the department by ID
        Department existingDepartment = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + request.getDepartmentId()));

        // Find the position by ID
        Position existingPosition = positionRepository.findById(request.getPositionId())
                .orElseThrow(() -> new EntityNotFoundException("Position not found with ID: " + request.getPositionId()));

        Period periodToCreate = new Period();
        periodToCreate.setStartDate(request.getStartDate());
        periodToCreate.setEndDate(request.getEndDate());
        periodToCreate.setDepartmentId(existingDepartment);
        periodToCreate.setPositionId(existingPosition);
        periodToCreate.setEmployeeId(existingEmployee);

        // Save the period
        periodRepository.save(periodToCreate);

        // Fetch information for logging
        Long  employeeId = periodToCreate.getEmployeeId().getId();
        String periodSave = (periodToCreate.getStartDate() != null ? periodToCreate.getStartDate().toString() : "ERROR")
                + " - " + (periodToCreate.getEndDate() != null ? periodToCreate.getEndDate().toString() : "WORKING");

        // Log information about the saved period related to the employee
        logger.info("Period: {} was saved for Employee with ID: {}", periodSave, employeeId);
    }

    public boolean isLastPeriodClosed(Employee employee) {
        List<Period> periods = periodRepository.findPeriodByEmployeeId(employee);

        if (!periods.isEmpty()) {
            // Check if the last period is closed
            Period lastPeriod = periods.get(periods.size() - 1);
            return lastPeriod.getEndDate() != null;
        }

        return true;
    }

    @Override
    public void savePeriod(Period period) {
        periodRepository.save(period);
    }

    @Override
    public void updatePeriod(Long id, PeriodUpdateRequest request) {
        // Find the period by ID
        Optional<Period> existingPeriodOptional = periodRepository.findById(id);
        // Get the existing period or throw an exception
        Period existingPeriod = existingPeriodOptional.orElseThrow(() -> new EntityNotFoundException("Period not found with ID: " + id));

        // Find the department by ID
        Department existingDepartment = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + request.getDepartmentId()));

        // Find the position by ID
        Position existingPosition = positionRepository.findById(request.getPositionId())
                .orElseThrow(() -> new EntityNotFoundException("Position not found with ID: " + request.getPositionId()));

        Period periodToUpdate = new Period();
        periodToUpdate.setStartDate(request.getStartDate());
        periodToUpdate.setEndDate(request.getEndDate());
        periodToUpdate.setDepartmentId(existingDepartment);
        periodToUpdate.setPositionId(existingPosition);

        // Update the period using a query
        periodRepository.updatePeriod(
                id,
                periodToUpdate.getStartDate(),
                periodToUpdate.getEndDate(),
                periodToUpdate.getDepartmentId(),
                periodToUpdate.getPositionId()

        );

        // Log the successful update
        Employee employee = existingPeriod.getEmployeeId();
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        String period = existingPeriod.getStartDate() + " - " + existingPeriod.getEndDate();
        logger.info("Period: {} was updated for Employee: {} with ID: {}", period, fullName, id);

    }

    @Override
    public void deletePeriodForEmployee(Long periodId, Long employeeId) {

        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(employeeId);
        // Get the existing Employee or throw an exception
        Employee existingEmployee = existingEmployeeOptional.orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

        Optional<Period> existingPeriodOptional = periodRepository.findByIdAndEmployeeId(periodId, existingEmployee);
        // Get the existing Period or throw an exception
        Period periodToDelete = existingPeriodOptional.orElseThrow(() -> new EntityNotFoundException("Period not found with ID: " + periodId +
        " for employee with ID: " + employeeId));

        String fullName = existingEmployee.getFirstName() + " " + existingEmployee.getLastName();
        Long idPeriod = periodToDelete.getId();

        // Set employeeId to null
        periodToDelete.setEmployeeId(null);

        // Save the period
        periodRepository.save(periodToDelete);

        // Delete the period
        periodRepository.delete(periodToDelete);
        logger.info("Period: {} was deleted for Employee: {}", idPeriod, fullName);
    }

    @Override
    public Optional<Period> findPeriodById(Long id) {
        return periodRepository.findById(id);
    }

    @Override
    public PeriodResponse getAllPeriodsForEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        // Form and return the response.
        return buildResponse(employeeId);
    }

    private PeriodResponse buildResponse(Long employeeId) {
        // Retrieve the employee by ID
        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(employeeId);

        Employee existingEmployee = existingEmployeeOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with ID: " + employeeId));

        List<Period> periods = periodRepository.findPeriodByEmployeeId(existingEmployee);
        List<Department> departments = departmentRepository.findAll();
        List<Position> positions = positionRepository.findAll();

        // Check if periods are not found
        if (periods.isEmpty()) {
            logger.warn("Periods not found for employee with ID: {}", employeeId);
        }

        int age = calculateAge(existingEmployee.getBirthDate());

        String experience = calculateTotalExperience(periods);

        // Build and return the PeriodResponse
        return buildPeriodResponse(existingEmployee, age, experience, periods, departments, positions);
    }


    // Build the PeriodResponse
    private PeriodResponse buildPeriodResponse(Employee existingEmployee, int age, String experience, List<Period> periods,
                                               List<Department> departments, List<Position> positions) {

        return PeriodResponse.builder()
                .fullName(existingEmployee.getFirstName() + " " + existingEmployee.getLastName())
                .age(age)
                .experience(experience)
                .periods(periods)
                .departments(departments)
                .positions(positions)
                .build();
    }


    // Calculate the age
    private Integer calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return java.time.Period.between(birthDate, currentDate).getYears();
    }

    // Calculate the Total experience
    public static String calculateTotalExperience(List<Period> periods) {
        long totalYears = 0;
        long totalMonths = 0;

        for (Period period : periods) {
            LocalDate startDate = period.getStartDate();
            LocalDate endDate = period.getEndDate() != null ? period.getEndDate() : LocalDate.now();

            long years = ChronoUnit.YEARS.between(startDate, endDate);
            endDate = endDate.minusYears(years);

            long months = ChronoUnit.MONTHS.between(startDate, endDate);

            totalYears += years;
            totalMonths += months;
        }

        // Adjust months
        totalYears += totalMonths / 12;
        totalMonths %= 12;

        return String.format(
                "%d years, %d months",
                totalYears,
                totalMonths
        );
    }

}