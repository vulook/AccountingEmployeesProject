package edu.cbsystematics.com.accountingemployeesproject.repository;

import edu.cbsystematics.com.accountingemployeesproject.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}