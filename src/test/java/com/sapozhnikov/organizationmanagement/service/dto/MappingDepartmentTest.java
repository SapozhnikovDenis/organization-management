package com.sapozhnikov.organizationmanagement.service.dto;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.entity.EmployeeEntity;
import com.sapozhnikov.organizationmanagement.service.iml.DepartmentServiceImpl;
import com.sapozhnikov.organizationmanagement.utils.exception.ApiException;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetLeaderDepartmentRs;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class MappingDepartmentTest extends DepartmentServiceImpl {

    private static final String NAME = "development";
    private static final LocalDate CREATE_DATE = LocalDate.now();
    private static final long LEAD_ID = 987654321L;

    public MappingDepartmentTest() {
        super(null);
    }

    @Test
    public void mapToEntity() {
        CreateDepartmentRq createDepartmentRq = new CreateDepartmentRq();
        createDepartmentRq.setName(NAME);
        createDepartmentRq.setLeadId(LEAD_ID);
        createDepartmentRq.setCreateDate(CREATE_DATE);

        DepartmentEntity departmentEntity = mapToDepartmentEntity(createDepartmentRq);

        assertEquals(createDepartmentRq.getName(), departmentEntity.getName());
        assertEquals(createDepartmentRq.getCreateDate(), departmentEntity.getCreateDate());
        assertNull(departmentEntity.getLeadDepartment());
        assertTrue(departmentEntity.getSubordinatesDepartments().isEmpty());
    }

    @Test
    public void mapToGetDepartmentInfo() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setName(NAME);
        departmentEntity.setCreateDate(LocalDate.now());
        EmployeeEntity lead = new EmployeeEntity();
        lead.setLeadInDepartment(true);
        List<EmployeeEntity> employees = Arrays.asList(lead, new EmployeeEntity());
        departmentEntity.setEmployees(employees);

        GetDepartmentInfo getDepartmentInfo =
                mapToGetDepartmentInfo(departmentEntity);

        assertEquals(departmentEntity.getName(), getDepartmentInfo.getName());
        assertEquals(departmentEntity.getCreateDate(), getDepartmentInfo.getCreateDate());
        assertEquals(Long.valueOf(employees.size()), getDepartmentInfo.getEmployeeCount());
        assertNotNull(getDepartmentInfo.getLeader());
    }

    @Test(expected = ApiException.class)
    public void mapToGetDepartmentInfoNotHaveLead() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setName(NAME);
        departmentEntity.setCreateDate(LocalDate.now());
        List<EmployeeEntity> employees = Collections.singletonList(new EmployeeEntity());
        departmentEntity.setEmployees(employees);

        mapToGetDepartmentInfo(departmentEntity);
    }

    @Test
    public void mapToGetLeaderDepartmentRs() {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setFirstName("Karl");
        employeeEntity.setSecondName("Karlov");
        employeeEntity.setMiddleName("Karlovich");
        employeeEntity.setPhone("9999999999");
        employeeEntity.setPosition("lead developers");
        employeeEntity.setEmail("lead@test.com");

        GetLeaderDepartmentRs getLeaderDepartmentRs = mapToGetLeaderDepartmentRs(employeeEntity);

        assertEquals(employeeEntity.getFirstName(), getLeaderDepartmentRs.getFirstName());
        assertEquals(employeeEntity.getSecondName(), getLeaderDepartmentRs.getSecondName());
        assertEquals(employeeEntity.getMiddleName(), getLeaderDepartmentRs.getMiddleName());
        assertEquals(employeeEntity.getPhone(), getLeaderDepartmentRs.getPhone());
        assertEquals(employeeEntity.getPosition(), getLeaderDepartmentRs.getPosition());
        assertEquals(employeeEntity.getEmail(), getLeaderDepartmentRs.getEmail());
    }
}