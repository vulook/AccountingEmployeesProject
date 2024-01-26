package edu.cbsystematics.com.accountingemployeesproject.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@NoArgsConstructor
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "name_department", nullable = false, columnDefinition = "varchar(50)")
    private String nameDepartment;

    public Department(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

}
