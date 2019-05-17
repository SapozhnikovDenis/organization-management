package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.utils.exception.ApiException;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
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
        DepartmentEntity departmentEntity = departmentRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND));
        return mapToGetDepartmentInfo(departmentEntity);
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
        return getDepartmentInfo;
    }
}
