package com.sapozhnikov.organizationmanagement.service;

import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;

public interface DepartmentService {
    Long createDepartment(CreateDepartmentRq createDepartmentRq);
}
