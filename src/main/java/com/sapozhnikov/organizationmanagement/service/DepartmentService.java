package com.sapozhnikov.organizationmanagement.service;

import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;

public interface DepartmentService {
    Long createDepartment(CreateDepartmentRq createDepartmentRq);
    void renameDepartment(RenameDepartmentRq renameDepartmentRq);
    void removeDepartment(Long id);
}
