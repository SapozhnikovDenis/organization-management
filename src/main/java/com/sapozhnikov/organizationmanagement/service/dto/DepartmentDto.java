package com.sapozhnikov.organizationmanagement.service.dto;

import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DepartmentDto {
    private String name;
    private LocalDate createDate;
    private Long leadId;

    public static DepartmentDto of(CreateDepartmentRq departmentRq) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName(departmentRq.getName());
        departmentDto.setCreateDate(departmentRq.getCreateDate());
        departmentDto.setLeadId(departmentRq.getLeadId());
        return departmentDto;
    }
}
