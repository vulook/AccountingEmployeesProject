package edu.cbsystematics.com.accountingemployeesproject.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PeriodUpdateRequest {

    private LocalDate startDate;

    private LocalDate endDate;

    private Long departmentId;

    private Long positionId;

}