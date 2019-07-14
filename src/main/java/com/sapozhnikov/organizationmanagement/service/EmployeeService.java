package com.sapozhnikov.organizationmanagement.service;

import com.sapozhnikov.organizationmanagement.web.dto.department.GetSalaryDepartmentRs;
import com.sapozhnikov.organizationmanagement.web.dto.employee.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    GetSalaryDepartmentRs getSalaryFullDepartment(Long id);

    List<EmployeeDto> getEmployeesInDepartment(Long departmentId);
}
