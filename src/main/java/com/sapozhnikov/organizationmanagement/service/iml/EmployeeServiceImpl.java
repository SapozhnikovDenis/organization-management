package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.db.repository.EmployeeRepository;
import com.sapozhnikov.organizationmanagement.service.EmployeeService;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetSalaryDepartmentRs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
}