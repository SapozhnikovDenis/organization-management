package com.sapozhnikov.organizationmanagement.service;

import com.sapozhnikov.organizationmanagement.service.dto.DepartmentDto;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;

public interface DepartmentService {
    Long createDepartment(DepartmentDto departmentDto);
    void renameDepartment(RenameDepartmentRq renameDepartmentRq);
    void removeDepartment(Long id);
}
