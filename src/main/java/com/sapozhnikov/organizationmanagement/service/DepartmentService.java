package com.sapozhnikov.organizationmanagement.service;

import com.sapozhnikov.organizationmanagement.web.dto.department.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.web.dto.department.ChangeLeaderDepartmentRq;
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

    void changeLeaderDepartment(ChangeLeaderDepartmentRq changeLeaderDepartmentRq);

    List<GetDepartmentInfo> getLeadDepartments(Long id);

    GetDepartmentInfo getDepartment(String name);

}
