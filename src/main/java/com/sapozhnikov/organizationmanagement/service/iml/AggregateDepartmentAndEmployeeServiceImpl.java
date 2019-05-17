package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.service.AggregateDepartmentAndEmployeeService;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.service.dto.GetLeadDepartmentAndEmployeeCount;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetDepartmentRs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AggregateDepartmentAndEmployeeServiceImpl implements AggregateDepartmentAndEmployeeService {

    private final DepartmentService departmentService;

    @Override
    public GetDepartmentRs getDepartmentWithEmployeeInfo(Long id) {
        GetDepartmentInfo departmentInfo = departmentService.getDepartmentInfo(id);
        //TODO get employeeInfo in employeeService
        GetLeadDepartmentAndEmployeeCount getLeadDepartmentAndEmployeeCount = new GetLeadDepartmentAndEmployeeCount();
        return mapToGetDepartmentRs(departmentInfo, getLeadDepartmentAndEmployeeCount);
    }

    protected GetDepartmentRs mapToGetDepartmentRs(GetDepartmentInfo departmentInfo,
                                                 GetLeadDepartmentAndEmployeeCount employeeInfo) {
        GetDepartmentRs getDepartmentRs = new GetDepartmentRs();
        getDepartmentRs.setName(departmentInfo.getName());
        getDepartmentRs.setCreateDate(departmentInfo.getCreateDate());
        getDepartmentRs.setEmployeeCount(employeeInfo.getEmployeeCount());
        getDepartmentRs.setLeader(employeeInfo.getLeader());
        return getDepartmentRs;
    }
}
