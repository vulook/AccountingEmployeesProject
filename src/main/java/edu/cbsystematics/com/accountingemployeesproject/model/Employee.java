package edu.cbsystematics.com.accountingemployeesproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "varchar(100)")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "varchar(100)")
    private String lastName;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "birth_date", nullable = false, columnDefinition = "varchar(20)")
    private LocalDate birthDate;

    @Column(name = "email", nullable = false, columnDefinition = "varchar(50)")
    private String email;

    @Column(name = "phone", nullable = false, columnDefinition = "varchar(50)")
    private String phoneNumber;

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "employeeId")
    private List<Period> periods;

    public Employee(String firstName, String lastName, LocalDate birthDate, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}
