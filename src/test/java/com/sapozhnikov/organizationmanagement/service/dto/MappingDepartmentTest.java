package com.sapozhnikov.organizationmanagement.service.dto;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.service.iml.DepartmentServiceImpl;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import org.junit.Test;

import java.time.LocalDate;

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
        assertNull(departmentEntity.getSubordinatesDepartments());
    }

    @Test
    public void mapToGetDepartmentInfo() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setName(NAME);
        departmentEntity.setCreateDate(LocalDate.now());

        GetDepartmentInfo getDepartmentInfo =
                mapToGetDepartmentInfo(departmentEntity);

        assertEquals(departmentEntity.getName(), getDepartmentInfo.getName());
        assertEquals(departmentEntity.getCreateDate(), getDepartmentInfo.getCreateDate());
    }
}