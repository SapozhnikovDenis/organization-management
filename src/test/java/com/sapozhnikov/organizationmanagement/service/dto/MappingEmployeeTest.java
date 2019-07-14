package com.sapozhnikov.organizationmanagement.service.dto;

import com.sapozhnikov.organizationmanagement.db.entity.EmployeeEntity;
import com.sapozhnikov.organizationmanagement.service.iml.EmployeeServiceImpl;
import com.sapozhnikov.organizationmanagement.web.dto.employee.EmployeeDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class MappingEmployeeTest extends EmployeeServiceImpl {

    public MappingEmployeeTest() {
        super(null);
    }

    @Test
    public void mapToEmployeeDto() {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setFirstName("FirstName");
        employeeEntity.setSecondName("SecondName");
        employeeEntity.setMiddleName("MiddleName");
        employeeEntity.setBirthDate(LocalDate.now());
        employeeEntity.setDismissalDate(LocalDate.now());
        employeeEntity.setEmail("Email");
        employeeEntity.setEmploymentDate(LocalDate.now());
        employeeEntity.setLeadInDepartment(true);
        employeeEntity.setPhone("Phone");
        employeeEntity.setSalary(BigDecimal.ONE);
        employeeEntity.setSex("Sex");

        EmployeeDto employeeDto = mapToEmployeeDto(employeeEntity);

        assertEquals(employeeEntity.getFirstName(), employeeDto.getFirstName());
        assertEquals(employeeEntity.getSecondName(), employeeDto.getSecondName());
        assertEquals(employeeEntity.getMiddleName(), employeeDto.getMiddleName());
        assertEquals(employeeEntity.getBirthDate(), employeeDto.getBirthDate());
        assertEquals(employeeEntity.getDismissalDate(), employeeDto.getDismissalDate());
        assertEquals(employeeEntity.getEmail(), employeeDto.getEmail());
        assertEquals(employeeEntity.getEmploymentDate(), employeeDto.getEmploymentDate());
        assertEquals(employeeEntity.getLeadInDepartment(), employeeDto.getLeadInDepartment());
        assertEquals(employeeEntity.getPhone(), employeeDto.getPhone());
        assertEquals(employeeEntity.getSalary(), employeeDto.getSalary());
        assertEquals(employeeEntity.getSex(), employeeDto.getSex());
    }
}