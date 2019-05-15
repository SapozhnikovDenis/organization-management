package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.DepartmentDto;
import com.sapozhnikov.organizationmanagement.utils.exception.ApiException;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Long createDepartment(DepartmentDto departmentDto) {
        DepartmentEntity departmentEntity = departmentDto.mapToEntity();
        Long leadId = departmentDto.getLeadId();
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
}
