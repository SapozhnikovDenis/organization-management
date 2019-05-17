package com.sapozhnikov.organizationmanagement.service.iml.integration;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.config.TestConfigurationJpa;
import com.sapozhnikov.organizationmanagement.service.dto.DepartmentDto;
import com.sapozhnikov.organizationmanagement.service.iml.DepartmentServiceImpl;
import com.sapozhnikov.organizationmanagement.utils.exception.ApiException;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;
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
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(classes = TestConfigurationJpa.class)
public class DepartmentServiceImplIntegrationTest {
    //TODO skip integration tests in maven package
    private static final String QA = "qa";
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

    @Test
    public void renameDepartmentSuccessful() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        String departmentName = DEVELOP;
        departmentEntity.setName(departmentName);
        DepartmentEntity saveDepartmentEntity = departmentRepository.save(departmentEntity);
        RenameDepartmentRq renameDepartmentRq = new RenameDepartmentRq();
        Long saveDepartmentEntityId = saveDepartmentEntity.getId();
        renameDepartmentRq.setId(saveDepartmentEntityId);
        renameDepartmentRq.setNewName(QA);

        departmentService.renameDepartment(renameDepartmentRq);

        DepartmentEntity renameDepartmentEntity = departmentRepository.getOne(saveDepartmentEntityId);
        assertEquals(renameDepartmentRq.getNewName(), renameDepartmentEntity.getName());
        assertNotEquals(departmentName, renameDepartmentEntity.getName());
    }

    @Test(expected = ApiException.class)
    public void renameDepartmentNotFoundDepartment() {
        RenameDepartmentRq renameDepartmentRq = new RenameDepartmentRq();
        renameDepartmentRq.setId(1L);
        renameDepartmentRq.setNewName(QA);

        assertFalse(departmentRepository.existsById(renameDepartmentRq.getId()));
        departmentService.renameDepartment(renameDepartmentRq);
    }

    @Test
    public void removeDepartmentSuccessful() {
        DepartmentEntity saveDepartmentEntity = departmentRepository.save(new DepartmentEntity());
        Long id = saveDepartmentEntity.getId();

        departmentService.removeDepartment(id);

        assertFalse(departmentRepository.existsById(id));
    }

    @Test(expected = ApiException.class)
    public void removeDepartmentNotFoundDepartment() {
        long id = 1L;
        assertFalse(departmentRepository.existsById(id));
        departmentService.removeDepartment(id);
    }
}

