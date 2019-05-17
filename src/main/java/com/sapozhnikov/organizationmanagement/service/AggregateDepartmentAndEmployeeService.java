package com.sapozhnikov.organizationmanagement.service;

import com.sapozhnikov.organizationmanagement.web.dto.department.GetDepartmentRs;

public interface AggregateDepartmentAndEmployeeService {
    GetDepartmentRs getDepartmentWithEmployeeInfo(Long id);
}
