package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.entity.EmployeeEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.utils.exception.ApiException;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetLeaderDepartmentRs;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Long createDepartment(CreateDepartmentRq createDepartmentRq) {
        DepartmentEntity departmentEntity = mapToDepartmentEntity(createDepartmentRq);
        Long leadId = createDepartmentRq.getLeadId();
        if (leadId != null) {
            departmentRepository.findById(leadId)
                    .ifPresent(departmentEntity::setLeadDepartment);
        }
        DepartmentEntity saveDepartment = departmentRepository.save(departmentEntity);
        return saveDepartment.getId();
    }

    @Override
    public void renameDepartment(RenameDepartmentRq renameDepartmentRq) {
        Optional<DepartmentEntity> optionalDepartmentEntity =
                departmentRepository.findById(renameDepartmentRq.getId());
        if (optionalDepartmentEntity.isPresent()) {
            DepartmentEntity departmentEntity = optionalDepartmentEntity.get();
            departmentEntity.setName(renameDepartmentRq.getNewName());
            departmentRepository.save(departmentEntity);
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void removeDepartment(Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public GetDepartmentInfo getDepartmentInfo(Long id) {
        DepartmentEntity departmentEntity = getDepartmentEntityOrThrowNotFound(id);
        return mapToGetDepartmentInfo(departmentEntity);
    }

    @Override
    public List<GetDepartmentInfo> getDirectSubordinatesDepartments(Long id) {
        DepartmentEntity departmentEntity = getDepartmentEntityOrThrowNotFound(id);
        return departmentEntity.getSubordinatesDepartments().stream()
                .map(this::mapToGetDepartmentInfo)
                .collect(Collectors.toList());
    }

    protected DepartmentEntity mapToDepartmentEntity(CreateDepartmentRq createDepartmentRq) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setName(createDepartmentRq.getName());
        departmentEntity.setCreateDate(createDepartmentRq.getCreateDate());
        return departmentEntity;
    }

    protected GetDepartmentInfo mapToGetDepartmentInfo(DepartmentEntity departmentEntity) {
        GetDepartmentInfo getDepartmentInfo = new GetDepartmentInfo();
        getDepartmentInfo.setName(departmentEntity.getName());
        getDepartmentInfo.setCreateDate(departmentEntity.getCreateDate());
        List<EmployeeEntity> employees = departmentEntity.getEmployees();
        if (employees != null && !employees.isEmpty()) {
            getDepartmentInfo.setEmployeeCount((long) employees.size());
            Optional<EmployeeEntity> leadEmployee = employees.stream()
                    .filter(employee -> employee.getLeadInDepartment() != null
                            && employee.getLeadInDepartment())
                    .findFirst();
            EmployeeEntity employeeEntity = leadEmployee.orElseThrow(
                    () -> new ApiException(HttpStatus.CONFLICT));
            getDepartmentInfo.setLeader(mapToGetLeaderDepartmentRs(employeeEntity));
        }
        return getDepartmentInfo;
    }

    protected GetLeaderDepartmentRs mapToGetLeaderDepartmentRs(EmployeeEntity employeeEntity) {
        GetLeaderDepartmentRs getLeaderDepartmentRs = new GetLeaderDepartmentRs();
        getLeaderDepartmentRs.setFirstName(employeeEntity.getFirstName());
        getLeaderDepartmentRs.setMiddleName(employeeEntity.getMiddleName());
        getLeaderDepartmentRs.setSecondName(employeeEntity.getSecondName());
        getLeaderDepartmentRs.setEmail(employeeEntity.getEmail());
        getLeaderDepartmentRs.setPhone(employeeEntity.getPhone());
        getLeaderDepartmentRs.setPosition(employeeEntity.getPosition());
        return getLeaderDepartmentRs;
    }

    private DepartmentEntity getDepartmentEntityOrThrowNotFound(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND));
    }
}
