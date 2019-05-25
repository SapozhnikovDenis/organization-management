package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.db.entity.EmployeeEntity;
import com.sapozhnikov.organizationmanagement.db.repository.EmployeeRepository;
import com.sapozhnikov.organizationmanagement.service.EmployeeService;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetSalaryDepartmentRs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    @Before
    public void setUp() {
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    public void getSalaryFullDepartment() {
        long departmentId  = 1L;
        EmployeeEntity firstEmployee = new EmployeeEntity();
        firstEmployee.setSalary(BigDecimal.valueOf(200000.200D));
        EmployeeEntity secondEmployee = new EmployeeEntity();
        secondEmployee.setSalary(BigDecimal.valueOf(300000.300D));
        when(employeeRepository.findAllByDepartment_Id(departmentId))
                .thenReturn(Arrays.asList(firstEmployee, secondEmployee));

        GetSalaryDepartmentRs salaryFullDepartment = employeeService.getSalaryFullDepartment(departmentId);

        assertEquals(firstEmployee.getSalary().add(secondEmployee.getSalary()), salaryFullDepartment.getSalary());
    }
}