package com.sapozhnikov.organizationmanagement.service.dto;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class MappingDepartmentTest {

    private static final String NAME = "name";
    private static final LocalDate CREATE_DATE = LocalDate.now();
    private static final long LEAD_ID = 987654321L;

    @Test
    public void of() {
        CreateDepartmentRq createDepartmentRq = new CreateDepartmentRq();
        createDepartmentRq.setName(NAME);
        createDepartmentRq.setCreateDate(CREATE_DATE);
        createDepartmentRq.setLeadId(LEAD_ID);

        DepartmentDto departmentDto = DepartmentDto.of(createDepartmentRq);

        assertEquals(createDepartmentRq.getName(), departmentDto.getName());
        assertEquals(createDepartmentRq.getCreateDate(), departmentDto.getCreateDate());
        assertEquals(createDepartmentRq.getLeadId(), departmentDto.getLeadId());
    }

    @Test
    public void mapToEntity() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName(NAME);
        departmentDto.setLeadId(LEAD_ID);
        departmentDto.setCreateDate(CREATE_DATE);

        DepartmentEntity departmentEntity = departmentDto.mapToEntity();

        assertEquals(departmentDto.getName(), departmentEntity.getName());
        assertEquals(departmentDto.getCreateDate(), departmentEntity.getCreateDate());
        assertNull(departmentEntity.getLeadDepartment());
        assertNull(departmentEntity.getSubordinatesDepartments());
    }
}