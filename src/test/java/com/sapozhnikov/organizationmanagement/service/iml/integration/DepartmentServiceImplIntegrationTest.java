package com.sapozhnikov.organizationmanagement.service.iml.integration;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.config.TestConfigurationJpa;
import com.sapozhnikov.organizationmanagement.service.dto.DepartmentDto;
import com.sapozhnikov.organizationmanagement.service.iml.DepartmentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static junit.framework.TestCase.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(classes = TestConfigurationJpa.class)
public class DepartmentServiceImplIntegrationTest {
    //TODO skip integration tests in maven package
    private static final String DEVELOP = "develop";
    private static final long LEAD_ID = 987654321L;

    @Autowired
    private DepartmentRepository departmentRepository;

    private DepartmentService departmentService;

    @Before
    public void setUp() {
        departmentService = new DepartmentServiceImpl(departmentRepository);
    }

    @Test
    public void createDepartmentNotHaveLead() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName(DEVELOP);
        departmentDto.setCreateDate(LocalDate.now());

        Long departmentId = departmentService.createDepartment(departmentDto);

        DepartmentEntity departmentEntity = departmentRepository.getOne(departmentId);
        assertEquals(departmentDto.getName(), departmentEntity.getName());
        assertEquals(departmentDto.getCreateDate(), departmentEntity.getCreateDate());
        assertNull(departmentEntity.getLeadDepartment());
        assertNull(departmentEntity.getSubordinatesDepartments());
    }

    @Test
    public void createDepartmentWithLead() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName(DEVELOP);
        departmentDto.setCreateDate(LocalDate.now());
        departmentDto.setLeadId(LEAD_ID);
        DepartmentEntity leadDepartment = new DepartmentEntity();
        leadDepartment.setId(LEAD_ID);
        departmentRepository.save(leadDepartment);

        Long departmentId = departmentService.createDepartment(departmentDto);

        DepartmentEntity departmentEntity = departmentRepository.getOne(departmentId);
        assertEquals(departmentDto.getName(), departmentEntity.getName());
        assertEquals(departmentDto.getCreateDate(), departmentEntity.getCreateDate());
        assertNull(departmentEntity.getLeadDepartment());
        assertNull(departmentEntity.getSubordinatesDepartments());
    }
}

