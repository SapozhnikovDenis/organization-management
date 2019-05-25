package com.sapozhnikov.organizationmanagement.db.repository;

import com.sapozhnikov.organizationmanagement.db.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findAllByDepartment_Id(Long departmentId);
}
