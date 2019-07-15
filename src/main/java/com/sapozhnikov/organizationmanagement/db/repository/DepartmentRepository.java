package com.sapozhnikov.organizationmanagement.db.repository;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    Optional<DepartmentEntity> findByName(String name);
}
