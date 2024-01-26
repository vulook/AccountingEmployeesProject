package edu.cbsystematics.com.accountingemployeesproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;


@Data
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "period")
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "start_date", nullable = false, columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "DATE")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false, columnDefinition = "BIGINT")
    private Department departmentId;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false, columnDefinition = "BIGINT")
    private Position positionId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employeeId;

    public Period(LocalDate startDate, LocalDate endDate, Department departmentId, Position positionId, Employee employeeId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.departmentId = departmentId;
        this.positionId = positionId;
        this.employeeId = employeeId;
    }

    public Period(LocalDate startDate, LocalDate endDate, Department departmentId, Position positionId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.departmentId = departmentId;
        this.positionId = positionId;
    }

}
