package edu.cbsystematics.com.accountingemployeesproject.model.response;

import edu.cbsystematics.com.accountingemployeesproject.model.Department;
import edu.cbsystematics.com.accountingemployeesproject.model.Period;
import edu.cbsystematics.com.accountingemployeesproject.model.Position;
import lombok.*;

import java.util.List;


@Builder
public record PeriodResponse(String fullName, int age, String experience,
                             List<Period> periods, List<Department> departments, List<Position> positions) {

}