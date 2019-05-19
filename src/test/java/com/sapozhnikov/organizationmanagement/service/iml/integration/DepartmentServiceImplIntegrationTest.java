package com.sapozhnikov.organizationmanagement.service.iml.integration;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.service.iml.DepartmentServiceImpl;
import com.sapozhnikov.organizationmanagement.utils.exception.ApiException;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(initializers = {DepartmentServiceImplIntegrationTest.Initializer.class})
public class DepartmentServiceImplIntegrationTest {

    private static final String QA = "qa";
    private static final String DEVELOP = "develop";
    private static final long LEAD_ID = 987654321L;

    @Autowired
    private DepartmentRepository departmentRepository;

    private DepartmentService departmentService;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer();

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Before
    public void setUp() {
        departmentService = new DepartmentServiceImpl(departmentRepository);
    }

    @Test
    public void createDepartmentNotHaveLead() {
        CreateDepartmentRq createDepartmentRq = new CreateDepartmentRq();
        createDepartmentRq.setName(DEVELOP);
        createDepartmentRq.setCreateDate(LocalDate.now());

        Long departmentId = departmentService.createDepartment(createDepartmentRq);

        DepartmentEntity departmentEntity = departmentRepository.getOne(departmentId);
        assertEquals(createDepartmentRq.getName(), departmentEntity.getName());
        assertEquals(createDepartmentRq.getCreateDate(), departmentEntity.getCreateDate());
        assertNull(departmentEntity.getLeadDepartment());
        assertNull(departmentEntity.getSubordinatesDepartments());
    }

    @Test
    public void createDepartmentWithLead() {
        CreateDepartmentRq createDepartmentRq = new CreateDepartmentRq();
        createDepartmentRq.setName(DEVELOP);
        createDepartmentRq.setCreateDate(LocalDate.now());
        createDepartmentRq.setLeadId(LEAD_ID);
        DepartmentEntity leadDepartment = new DepartmentEntity();
        leadDepartment.setId(LEAD_ID);
        departmentRepository.save(leadDepartment);

        Long departmentId = departmentService.createDepartment(createDepartmentRq);

        DepartmentEntity departmentEntity = departmentRepository.getOne(departmentId);
        assertEquals(createDepartmentRq.getName(), departmentEntity.getName());
        assertEquals(createDepartmentRq.getCreateDate(), departmentEntity.getCreateDate());
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

    @Test
    public void getDepartmentInfoSuccessful() {
        DepartmentEntity saveDepartmentEntity = departmentRepository.save(new DepartmentEntity());
        Long id = saveDepartmentEntity.getId();

        GetDepartmentInfo getDepartmentInfo = departmentService.getDepartmentInfo(id);

        assertNotNull(getDepartmentInfo);
    }

    @Test(expected = ApiException.class)
    public void getDepartmentInfoNotFoundDepartment() {
        long id = 1L;
        assertFalse(departmentRepository.existsById(id));
        departmentService.getDepartmentInfo(id);
    }
}

