package edu.cbsystematics.com.accountingemployeesproject.repository;

import edu.cbsystematics.com.accountingemployeesproject.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

}