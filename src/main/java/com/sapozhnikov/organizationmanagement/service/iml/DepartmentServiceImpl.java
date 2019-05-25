package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.entity.EmployeeEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.utils.exception.ApiException;
import com.sapozhnikov.organizationmanagement.web.dto.department.ChangeLeaderDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetLeaderDepartmentRs;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private static final String LEAD_NOT_FOUND_MESSAGE = "lead department not found!";
    private static final String TARGET_DEPARTMENT_NOT_FOUND_MESSAGE = "target department not found!";

    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public Long createDepartment(CreateDepartmentRq createDepartmentRq) {
        DepartmentEntity department = mapToDepartmentEntity(createDepartmentRq);
        Long leadId = createDepartmentRq.getLeadId();
        DepartmentEntity saveDepartment;
        if (leadId != null) {
            DepartmentEntity leadDepartment = findDepartmentOrThrowNotFound(leadId, LEAD_NOT_FOUND_MESSAGE);
            department.setLeadDepartment(leadDepartment);
            saveDepartment = departmentRepository.save(department);
            leadDepartment.getSubordinatesDepartments().add(saveDepartment);
            departmentRepository.save(leadDepartment);
        } else {
            saveDepartment = departmentRepository.save(department);
        }
        return saveDepartment.getId();
    }

    @Override
    @Transactional
    public void renameDepartment(RenameDepartmentRq renameDepartmentRq) {
        DepartmentEntity department = findDepartmentOrThrowNotFound(renameDepartmentRq.getId());
        department.setName(renameDepartmentRq.getNewName());
        departmentRepository.save(department);
    }

    @Override
    @Transactional
    public void removeDepartment(Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public GetDepartmentInfo getDepartmentInfo(Long id) {
        DepartmentEntity department = findDepartmentOrThrowNotFound(id);
        return mapToGetDepartmentInfo(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetDepartmentInfo> getDirectSubordinatesDepartments(Long id) {
        DepartmentEntity department = findDepartmentOrThrowNotFound(id, TARGET_DEPARTMENT_NOT_FOUND_MESSAGE);
        return department.getSubordinatesDepartments().stream()
                .map(this::mapToGetDepartmentInfo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetDepartmentInfo> getAllSubordinatesDepartments(Long id) {
        DepartmentEntity department = findDepartmentOrThrowNotFound(id);
        HashSet<DepartmentEntity> uniqueDepartments = new HashSet<>();
        putAllSubordinatesDepartments(department, uniqueDepartments);
        return uniqueDepartments.stream()
                .map(this::mapToGetDepartmentInfo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changeLeaderDepartment(ChangeLeaderDepartmentRq changeLeaderDepartmentRq) {
        DepartmentEntity department = findDepartmentOrThrowNotFound(
                changeLeaderDepartmentRq.getDepartmentId(), TARGET_DEPARTMENT_NOT_FOUND_MESSAGE);
        DepartmentEntity newLeadDepartment = findDepartmentOrThrowNotFound(
                changeLeaderDepartmentRq.getNewLeadId(), LEAD_NOT_FOUND_MESSAGE);
        DepartmentEntity oldLeadDepartment = department.getLeadDepartment();
        oldLeadDepartment.getSubordinatesDepartments().remove(department);
        department.setLeadDepartment(newLeadDepartment);
        newLeadDepartment.getSubordinatesDepartments().add(department);
        departmentRepository.saveAll(Arrays.asList(oldLeadDepartment, department, newLeadDepartment));
    }

    @Override
    @Transactional
    public List<GetDepartmentInfo> getLeadDepartments(Long id) {
        DepartmentEntity department = findDepartmentOrThrowNotFound(id, TARGET_DEPARTMENT_NOT_FOUND_MESSAGE);
        Set<DepartmentEntity> leadDepartments = new HashSet<>();
        putAllLeadDepartments(department, leadDepartments);
        return leadDepartments.stream()
                .map(this::mapToGetDepartmentInfo)
                .collect(Collectors.toList());
    }

    private void putAllLeadDepartments(DepartmentEntity department, Set<DepartmentEntity> leadDepartments) {
        DepartmentEntity leadDepartment = department.getLeadDepartment();
        if (leadDepartment != null) {
            leadDepartments.add(leadDepartment);
            putAllLeadDepartments(leadDepartment, leadDepartments);
        }
    }

    private void putAllSubordinatesDepartments(DepartmentEntity departmentEntity,
                                               Set<DepartmentEntity> uniqueDepartments) {
        if (departmentEntity.getSubordinatesDepartments() != null) {
            departmentEntity.getSubordinatesDepartments().forEach(subordinatesDepartment -> {
                uniqueDepartments.add(subordinatesDepartment);
                putAllSubordinatesDepartments(subordinatesDepartment, uniqueDepartments);
            });
        }
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
                    () -> new ApiException("department not have lead!", HttpStatus.CONFLICT));
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

    private DepartmentEntity findDepartmentOrThrowNotFound(Long id) {
        return findDepartmentOrThrowNotFound(id, null);
    }

    private DepartmentEntity findDepartmentOrThrowNotFound(Long id, String throwMessage) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ApiException(throwMessage, HttpStatus.NOT_FOUND));
    }
}
