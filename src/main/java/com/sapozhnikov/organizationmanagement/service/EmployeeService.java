package com.sapozhnikov.organizationmanagement.service;

import com.sapozhnikov.organizationmanagement.web.dto.department.GetSalaryDepartmentRs;

public interface EmployeeService {
    GetSalaryDepartmentRs getSalaryFullDepartment(Long id);
}
