package com.sapozhnikov.organizationmanagement.db.repository;

import com.sapozhnikov.organizationmanagement.db.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
