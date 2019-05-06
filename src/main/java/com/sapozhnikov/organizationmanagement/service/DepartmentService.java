package com.sapozhnikov.organizationmanagement.service;

import com.sapozhnikov.organizationmanagement.service.dto.DepartmentDto;

public interface DepartmentService {
    Long createDepartment(DepartmentDto departmentDto);
}
