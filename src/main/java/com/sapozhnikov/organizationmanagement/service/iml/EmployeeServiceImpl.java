package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.db.entity.EmployeeEntity;
import com.sapozhnikov.organizationmanagement.db.repository.EmployeeRepository;
import com.sapozhnikov.organizationmanagement.service.EmployeeService;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetSalaryDepartmentRs;
import com.sapozhnikov.organizationmanagement.web.dto.employee.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public GetSalaryDepartmentRs getSalaryFullDepartment(Long departmentId) {
        double sumSalary = employeeRepository.findAllByDepartmentId(departmentId).stream()
                .mapToDouble(employee -> employee.getSalary().doubleValue())
                .sum();
        return new GetSalaryDepartmentRs(BigDecimal.valueOf(sumSalary));
    }

    @Override
    public List<EmployeeDto> getEmployeesInDepartment(Long departmentId) {
        return employeeRepository.findAllByDepartmentId(departmentId).stream()
                .map(this::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    protected EmployeeDto mapToEmployeeDto(EmployeeEntity employeeEntity) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName(employeeEntity.getFirstName());
        employeeDto.setSecondName(employeeEntity.getSecondName());
        employeeDto.setMiddleName(employeeEntity.getMiddleName());
        employeeDto.setBirthDate(employeeEntity.getBirthDate());
        employeeDto.setDismissalDate(employeeEntity.getDismissalDate());
        employeeDto.setEmail(employeeEntity.getEmail());
        employeeDto.setEmploymentDate(employeeEntity.getEmploymentDate());
        employeeDto.setLeadInDepartment(employeeEntity.getLeadInDepartment());
        employeeDto.setPhone(employeeEntity.getPhone());
        employeeDto.setPosition(employeeEntity.getPosition());
        employeeDto.setSalary(employeeEntity.getSalary());
        employeeDto.setSex(employeeEntity.getSex());
        return employeeDto;
    }
}