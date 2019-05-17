package com.sapozhnikov.organizationmanagement.service.dto;

import com.sapozhnikov.organizationmanagement.web.dto.department.GetLeaderDepartmentRs;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetLeadDepartmentAndEmployeeCount {
    private GetLeaderDepartmentRs leader;
    private Long employeeCount;
}
