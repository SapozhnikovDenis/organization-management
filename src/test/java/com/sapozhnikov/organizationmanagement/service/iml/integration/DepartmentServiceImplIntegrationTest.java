package com.sapozhnikov.organizationmanagement.service.iml.integration;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.service.iml.DepartmentServiceImpl;
import com.sapozhnikov.organizationmanagement.utils.exception.ApiException;
import com.sapozhnikov.organizationmanagement.web.dto.department.ChangeLeaderDepartmentRq;
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
import java.util.*;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
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
        assertTrue(departmentEntity.getSubordinatesDepartments().isEmpty());
    }

    @Test
    public void createDepartmentWithLead() {
        CreateDepartmentRq createDepartmentRq = new CreateDepartmentRq();
        createDepartmentRq.setName(DEVELOP);
        createDepartmentRq.setCreateDate(LocalDate.now());
        DepartmentEntity leadDepartment = new DepartmentEntity();
        DepartmentEntity saveLeadDepartment = departmentRepository.save(leadDepartment);
        Long leadDepartmentId = saveLeadDepartment.getId();
        createDepartmentRq.setLeadId(leadDepartmentId);
        leadDepartment.setId(leadDepartmentId);

        Long departmentId = departmentService.createDepartment(createDepartmentRq);

        DepartmentEntity departmentEntity = departmentRepository.getOne(departmentId);
        assertEquals(createDepartmentRq.getName(), departmentEntity.getName());
        assertEquals(createDepartmentRq.getCreateDate(), departmentEntity.getCreateDate());
        assertNotNull(departmentEntity.getLeadDepartment());
        assertTrue(departmentEntity.getSubordinatesDepartments().isEmpty());
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

    @Test
    public void getDirectSubordinatesDepartmentsSuccessful() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        List<DepartmentEntity> subordinatesDepartments = Collections.singletonList(new DepartmentEntity());
        departmentEntity.setSubordinatesDepartments(subordinatesDepartments);
        DepartmentEntity saveDepartmentEntity = departmentRepository.save(departmentEntity);
        Long id = saveDepartmentEntity.getId();

        List<GetDepartmentInfo> directSubordinatesDepartments =
                departmentService.getDirectSubordinatesDepartments(id);

        assertEquals(subordinatesDepartments.size(), directSubordinatesDepartments.size());
    }

    @Test(expected = ApiException.class)
    public void getDirectSubordinatesDepartmentsNotFoundDepartment() {
        long id = 1L;

        departmentService.getDirectSubordinatesDepartments(id);
    }


    @Test
    public void getAllSubordinatesDepartments() {
        List<DepartmentEntity> secondLevelSubordinatesDepartments =
                Collections.singletonList(new DepartmentEntity());
        DepartmentEntity firstLevelDepartment = new DepartmentEntity();
        firstLevelDepartment.setSubordinatesDepartments(secondLevelSubordinatesDepartments);
        List<DepartmentEntity> firstLevelSubordinatesDepartments =
                Collections.singletonList(firstLevelDepartment);
        DepartmentEntity targetDepartment = new DepartmentEntity();
        targetDepartment.setSubordinatesDepartments(firstLevelSubordinatesDepartments);
        DepartmentEntity saveDepartmentEntity = departmentRepository.save(targetDepartment);
        Long id = saveDepartmentEntity.getId();

        List<GetDepartmentInfo> directSubordinatesDepartments =
                departmentService.getAllSubordinatesDepartments(id);

        int expectedSubordinatesDepartments = firstLevelSubordinatesDepartments.size() +
                secondLevelSubordinatesDepartments.size();
        assertEquals(expectedSubordinatesDepartments, directSubordinatesDepartments.size());
    }

    @Test
    public void getAllSubordinatesDepartmentsCheckUnique() {
        DepartmentEntity duplicate = new DepartmentEntity();
        List<DepartmentEntity> secondLevelSubordinatesDepartments = Arrays.asList(new DepartmentEntity(), duplicate);
        DepartmentEntity firstLevelDepartment = new DepartmentEntity();
        firstLevelDepartment.setSubordinatesDepartments(secondLevelSubordinatesDepartments);
        List<DepartmentEntity> firstLevelSubordinatesDepartments = Arrays.asList(firstLevelDepartment, duplicate);
        DepartmentEntity targetDepartment = new DepartmentEntity();
        targetDepartment.setSubordinatesDepartments(firstLevelSubordinatesDepartments);
        DepartmentEntity saveDepartmentEntity = departmentRepository.save(targetDepartment);
        Long id = saveDepartmentEntity.getId();

        List<GetDepartmentInfo> directSubordinatesDepartments =
                departmentService.getAllSubordinatesDepartments(id);

        Set<DepartmentEntity> uniqueDepartments = new HashSet<>();
        uniqueDepartments.addAll(firstLevelSubordinatesDepartments);
        uniqueDepartments.addAll(secondLevelSubordinatesDepartments);
        assertEquals(uniqueDepartments.size(), directSubordinatesDepartments.size());
    }



    @Test
    public void changeLeaderDepartmentSuccessful() {
        DepartmentEntity targetDepartment = new DepartmentEntity();
        DepartmentEntity oldLeadDepartment = new DepartmentEntity();
        targetDepartment.setLeadDepartment(oldLeadDepartment);
        oldLeadDepartment.setSubordinatesDepartments(new ArrayList<>());
        DepartmentEntity saveTargetDepartment = departmentRepository.save(targetDepartment);
        Long saveTargetDepartmentId = saveTargetDepartment.getId();
        DepartmentEntity newLeadDepartment = new DepartmentEntity();
        newLeadDepartment.setSubordinatesDepartments(new ArrayList<>());
        DepartmentEntity saveNewLeadDepartment = departmentRepository.save(newLeadDepartment);
        long saveNewLeadDepartmentId = saveNewLeadDepartment.getId();
        ChangeLeaderDepartmentRq changeLeaderDepartmentRq = new ChangeLeaderDepartmentRq();
        changeLeaderDepartmentRq.setDepartmentId(saveTargetDepartmentId);
        changeLeaderDepartmentRq.setNewLeadId(saveNewLeadDepartmentId);


        departmentService.changeLeaderDepartment(changeLeaderDepartmentRq);
    }

    @Test(expected = ApiException.class)
    public void changeLeaderDepartmentNotFoundDepartment() {
        ChangeLeaderDepartmentRq changeLeaderDepartmentRq = new ChangeLeaderDepartmentRq();
        changeLeaderDepartmentRq.setDepartmentId(1L);
        changeLeaderDepartmentRq.setNewLeadId(2L);

        departmentService.changeLeaderDepartment(changeLeaderDepartmentRq);
    }
}

