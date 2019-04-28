package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Long createDepartment(CreateDepartmentRq createDepartmentRq) {
        Long leadId = createDepartmentRq.getLeadId();
        DepartmentEntity department = new DepartmentEntity();
        department.setName(createDepartmentRq.getName());
        department.setCreateDate(createDepartmentRq.getCreateDate());
        if (leadId != null) {
            departmentRepository.findById(leadId)
                    .ifPresent(department::setLeadDepartment);
        }
        DepartmentEntity saveDepartment = departmentRepository.save(department);
        return saveDepartment.getId();
    }
}
