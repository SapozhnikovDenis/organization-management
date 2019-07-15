package com.sapozhnikov.organizationmanagement.service.iml.integration;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.entity.EmployeeEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.db.repository.EmployeeRepository;
import com.sapozhnikov.organizationmanagement.service.EmployeeService;
import com.sapozhnikov.organizationmanagement.service.iml.EmployeeServiceImpl;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetSalaryDepartmentRs;
import com.sapozhnikov.organizationmanagement.web.dto.employee.EmployeeDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;


public class EmployeeServiceImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    private EmployeeService employeeService;

    @Before
    public void setUp() {
        employeeService = new EmployeeServiceImpl(employeeRepository);
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


    @Test
    public void createEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("FirstName");
        employeeDto.setSecondName("SecondName");
        employeeDto.setMiddleName("MiddleName");
        employeeDto.setBirthDate(LocalDate.now());
        employeeDto.setDismissalDate(LocalDate.now());
        employeeDto.setEmail("Email");
        employeeDto.setEmploymentDate(LocalDate.now());
        employeeDto.setLeadInDepartment(true);
        employeeDto.setPhone("Phone");
        employeeDto.setSalary(BigDecimal.ONE);
        employeeDto.setSex("Sex");
        employeeDto.setPosition("Position");

        EmployeeDto employeeDtoFromDb = employeeService.createEmployee(employeeDto);

        assertNotNull(employeeDtoFromDb.getId());
        assertTrue(employeeRepository.findById(employeeDtoFromDb.getId()).isPresent());
    }
}
