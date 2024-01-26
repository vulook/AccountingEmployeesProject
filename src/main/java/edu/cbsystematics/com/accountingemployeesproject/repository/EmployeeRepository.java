package edu.cbsystematics.com.accountingemployeesproject.repository;

import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long> {


    // Updates an employee's information based on ID
    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.firstName = :firstName, e.lastName = :lastName, e.birthDate= :birthDate, e.email = :email, e.phoneNumber = :phoneNumber WHERE e.id = :id")
    void updateEmployee(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastNam,
            @Param("birthDate") LocalDate birthDate,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber
    );

    // Retrieves a list of employees belonging to a specific department
    @Query("SELECT e FROM Employee e JOIN e.periods d WHERE d.departmentId.nameDepartment = :name")
    List<Employee> employeesByDepartment(@Param("name") String name);

    // Retrieves a list of employees holding a specific position
    @Query("SELECT e FROM Employee e JOIN e.periods p WHERE p.positionId.namePosition = :name")
    List<Employee> employeesByPosition(@Param("name") String name);

    // Retrieves a list of employees within a specific age range
    @Query("SELECT e FROM Employee e WHERE e.birthDate <= :maxDate AND e.birthDate >= :minDate")
    List<Employee> findByAgeRange(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);

    // Retrieves employees with birthdays between specified dates
    List<Employee> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    // Retrieves employees with birthdays before a specified date
    List<Employee> findByBirthDateBefore(LocalDate birthDateLimit);

    // Retrieves employees with birthdays less than a specified date
    List<Employee> findByBirthDateAfter(LocalDate birthDateLimit);

}