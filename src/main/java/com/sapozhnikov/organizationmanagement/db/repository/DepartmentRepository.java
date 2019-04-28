package com.sapozhnikov.organizationmanagement.db.repository;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
