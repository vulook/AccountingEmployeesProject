package edu.cbsystematics.com.accountingemployeesproject.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT(6)")
    private Long id;

    @Column(name = "name_position", nullable = false, columnDefinition = "varchar(100)")
    String namePosition;

    public Position(String namePosition) {
        this.namePosition = namePosition;
    }

}
