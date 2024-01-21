package edu.cbsystematics.com.accountingemployeesproject.service;

import edu.cbsystematics.com.accountingemployeesproject.model.Period;
import edu.cbsystematics.com.accountingemployeesproject.model.request.PeriodCreateRequest;
import edu.cbsystematics.com.accountingemployeesproject.model.request.PeriodUpdateRequest;
import edu.cbsystematics.com.accountingemployeesproject.model.response.PeriodResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PeriodService {

	// Create a new period
	@Transactional
    void createPeriod(PeriodCreateRequest request);

	void savePeriod(Period period);

	// Update an existing period with new details
    void updatePeriod(Long id, PeriodUpdateRequest request);

    // Delete a period by its ID
	void deletePeriodForEmployee(Long periodId, Long employeeId);

	// Find a period by its ID and return it as an optional
	Optional<Period> findPeriodById(Long id);

	// Get all periods associated with a specific employee
	PeriodResponse getAllPeriodsForEmployee(Long employeeId);

}