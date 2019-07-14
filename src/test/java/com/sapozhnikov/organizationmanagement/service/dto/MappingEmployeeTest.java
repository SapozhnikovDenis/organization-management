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
        employeeEntity.setId(123L);
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
        employeeEntity.setPosition("Position");

        EmployeeDto employeeDto = mapToEmployeeDto(employeeEntity);

        assertEquals(employeeDto.getId(), employeeEntity.getId());
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
        assertEquals(employeeEntity.getPosition(), employeeDto.getPosition());
    }

    @Test
    public void mapToEmployeeEntity() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(123L);
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

        EmployeeEntity employeeEntity = mapToEmployeeEntity(employeeDto);

        assertEquals(employeeDto.getId(), employeeEntity.getId());
        assertEquals(employeeDto.getFirstName(), employeeEntity.getFirstName());
        assertEquals(employeeDto.getSecondName(), employeeEntity.getSecondName());
        assertEquals(employeeDto.getMiddleName(), employeeEntity.getMiddleName());
        assertEquals(employeeDto.getBirthDate(), employeeEntity.getBirthDate());
        assertEquals(employeeDto.getDismissalDate(), employeeEntity.getDismissalDate());
        assertEquals(employeeDto.getEmail(), employeeEntity.getEmail());
        assertEquals(employeeDto.getEmploymentDate(), employeeEntity.getEmploymentDate());
        assertEquals(employeeDto.getLeadInDepartment(), employeeEntity.getLeadInDepartment());
        assertEquals(employeeDto.getPhone(), employeeEntity.getPhone());
        assertEquals(employeeDto.getSalary(), employeeEntity.getSalary());
        assertEquals(employeeDto.getSex(), employeeEntity.getSex());
        assertEquals(employeeDto.getPosition(), employeeEntity.getPosition());
    }
}