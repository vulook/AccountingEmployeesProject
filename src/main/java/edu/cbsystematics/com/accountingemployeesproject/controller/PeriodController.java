package edu.cbsystematics.com.accountingemployeesproject.controller;

import edu.cbsystematics.com.accountingemployeesproject.model.Period;
import edu.cbsystematics.com.accountingemployeesproject.model.request.PeriodCreateRequest;
import edu.cbsystematics.com.accountingemployeesproject.model.request.PeriodUpdateRequest;
import edu.cbsystematics.com.accountingemployeesproject.model.response.PeriodResponse;
import edu.cbsystematics.com.accountingemployeesproject.service.PeriodService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/periods")
public class PeriodController {

    private final PeriodService periodService;

    @Autowired
    public PeriodController(PeriodService periodService) {
        this.periodService = periodService;
    }


    // Create a new period
    @PostMapping("/add")
    public ResponseEntity<?> savePeriod(@RequestBody PeriodCreateRequest request) {
        try {
            periodService.createPeriod(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException | EntityNotFoundException ex) {
            // Handle IllegalStateException or EntityNotFoundException
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    // Update an existing period by ID
    @PutMapping("/update/{periodId}")
    public ResponseEntity<Void> updatePeriod(@PathVariable Long periodId, @RequestBody PeriodUpdateRequest request) {
        return periodService.findPeriodById(periodId)
                .map(existingPeriod -> {
                    // Call the service method to update the period
                    periodService.updatePeriod(periodId, request);
                    return ResponseEntity.ok().<Void>build(); // Return 200 "OK"
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Return 404 "Not Found" if period not found
    }

    // Delete an existing period by ID
    @DeleteMapping("/delete/{periodId}/{employeeId}")
    public ResponseEntity<Void> deletePeriodForEmployee(@PathVariable Long periodId, @PathVariable Long employeeId) {
        return periodService.findPeriodById(periodId)
                .map(existingPeriod -> {
                    // Call the service method to delete the period
                    periodService.deletePeriodForEmployee(periodId, employeeId);
                    return ResponseEntity.ok().<Void>build(); // Return 200 "OK"
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Return 404 "Not Found"
    }

    // Get a specific period by ID
    @GetMapping("/{id}")
    public ResponseEntity<Period> getPeriodById(@PathVariable Long id) {
        return periodService.findPeriodById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // Return 404 "Not Found"
    }

    // Get all periods for a specific employee and return a PeriodResponse
    @GetMapping("/all/{employeeId}")
    public ResponseEntity<PeriodResponse> getAllPeriodsForEmployee(@PathVariable Long employeeId) {
        PeriodResponse periodResponse = periodService.getAllPeriodsForEmployee(employeeId);
        return ResponseEntity.ok(periodResponse);
    }

}