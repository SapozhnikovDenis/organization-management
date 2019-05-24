package com.sapozhnikov.organizationmanagement.service;

import com.sapozhnikov.organizationmanagement.service.dto.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;

import java.util.List;

public interface DepartmentService {

    Long createDepartment(CreateDepartmentRq createDepartmentRq);

    void renameDepartment(RenameDepartmentRq renameDepartmentRq);

    void removeDepartment(Long id);

    GetDepartmentInfo getDepartmentInfo(Long id);

    List<GetDepartmentInfo> getDirectSubordinatesDepartments(Long id);

    List<GetDepartmentInfo> getAllSubordinatesDepartments(Long id);
}
