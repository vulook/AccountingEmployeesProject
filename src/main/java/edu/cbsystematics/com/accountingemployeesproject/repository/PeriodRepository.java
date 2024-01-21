package edu.cbsystematics.com.accountingemployeesproject.repository;

import edu.cbsystematics.com.accountingemployeesproject.model.Department;
import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import edu.cbsystematics.com.accountingemployeesproject.model.Period;
import edu.cbsystematics.com.accountingemployeesproject.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {

    // Updates the period details based on ID
    @Modifying
    @Transactional
    @Query("UPDATE Period p SET p.startDate = :startDate, p.endDate = :endDate, p.departmentId = :departmentId, p.positionId = :positionId WHERE p.id = :id")
    void updatePeriod(
            @Param("id") Long id,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("departmentId") Department departmentId,
            @Param("positionId") Position positionId
    );

    // Retrieves periods associated with a specific employee ID
    List<Period> findPeriodByEmployeeId(Employee employeeId);

    Optional<Period> findByIdAndEmployeeId(Long id, Employee employeeId);

}