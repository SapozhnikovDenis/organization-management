package com.sapozhnikov.organizationmanagement.service.dto;

import com.sapozhnikov.organizationmanagement.web.dto.department.GetLeaderDepartmentRs;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GetDepartmentInfo {
    private String name;
    private LocalDate createDate;
    private GetLeaderDepartmentRs leader;
    private Long employeeCount;
}
