package com.sapozhnikov.organizationmanagement.service.dto;

import com.sapozhnikov.organizationmanagement.service.iml.AggregateDepartmentAndEmployeeServiceImpl;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetDepartmentRs;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetLeaderDepartmentRs;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class MappingAggregateDepartmentAndEmployeeServiceTest extends AggregateDepartmentAndEmployeeServiceImpl {
    public MappingAggregateDepartmentAndEmployeeServiceTest() {
        super(null);
    }

    @Test
    public void mapToGetDepartmentRs() {
        GetDepartmentInfo departmentInfo = new GetDepartmentInfo();
        departmentInfo.setCreateDate(LocalDate.now());
        departmentInfo.setName("development");
        GetLeadDepartmentAndEmployeeCount employeeInfo = new GetLeadDepartmentAndEmployeeCount();
        employeeInfo.setEmployeeCount(1L);
        employeeInfo.setLeader(new GetLeaderDepartmentRs());

        GetDepartmentRs getDepartmentRs = mapToGetDepartmentRs(departmentInfo, employeeInfo);

        assertEquals(departmentInfo.getName(), getDepartmentRs.getName());
        assertEquals(departmentInfo.getCreateDate(), getDepartmentRs.getCreateDate());
        assertEquals(employeeInfo.getEmployeeCount(), getDepartmentRs.getEmployeeCount());
        assertEquals(employeeInfo.getLeader(), getDepartmentRs.getLeader());
    }
}
