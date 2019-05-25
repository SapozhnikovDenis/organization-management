package com.sapozhnikov.organizationmanagement.service.iml.integration;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.entity.EmployeeEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.db.repository.EmployeeRepository;
import com.sapozhnikov.organizationmanagement.service.EmployeeService;
import com.sapozhnikov.organizationmanagement.service.iml.EmployeeServiceImpl;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetSalaryDepartmentRs;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(initializers = {EmployeeServiceImplIntegrationTest.Initializer.class})
public class EmployeeServiceImplIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    private EmployeeService employeeService;

    @Before
    public void setUp() {
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

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

    @Test
    public void getSalaryFullDepartment() {
        DepartmentEntity department = new DepartmentEntity();
        department.setName("dev");
        department.setCreateDate(LocalDate.now());
        DepartmentEntity saveDepartment = departmentRepository.save(department);
        EmployeeEntity firstEmployee = new EmployeeEntity();
        firstEmployee.setFirstName("name");
        firstEmployee.setSecondName("name");
        firstEmployee.setSex("MALE");
        firstEmployee.setBirthDate(LocalDate.now());
        firstEmployee.setPhone("9999999999");
        firstEmployee.setEmail("test@test.com");
        firstEmployee.setEmploymentDate(LocalDate.now());
        firstEmployee.setDepartment(saveDepartment);
        firstEmployee.setPosition("developer");
        firstEmployee.setLeadInDepartment(true);
        firstEmployee.setSalary(BigDecimal.valueOf(200000.200D));
        employeeRepository.save(firstEmployee);
        EmployeeEntity secondEmployee = new EmployeeEntity();
        secondEmployee.setSalary(BigDecimal.valueOf(300000.300D));
        secondEmployee.setFirstName("name");
        secondEmployee.setSecondName("name");
        secondEmployee.setSex("MALE");
        secondEmployee.setBirthDate(LocalDate.now());
        secondEmployee.setPhone("9999999999");
        secondEmployee.setEmail("test@test.com");
        secondEmployee.setEmploymentDate(LocalDate.now());
        secondEmployee.setDepartment(saveDepartment);
        secondEmployee.setPosition("developer");
        secondEmployee.setLeadInDepartment(true);
        secondEmployee.setDepartment(saveDepartment);
        employeeRepository.save(secondEmployee);

        GetSalaryDepartmentRs salaryFullDepartment = employeeService.getSalaryFullDepartment(saveDepartment.getId());

        assertEquals(firstEmployee.getSalary().add(secondEmployee.getSalary()), salaryFullDepartment.getSalary());
    }
}
