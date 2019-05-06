package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.DepartmentDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DepartmentServiceImplTest {

    private static final Long ID = 123456789L;
    private static final String DEVELOP = "develop";
    private static final long LEAD_ID = 987654321L;

    @Mock
    private DepartmentRepository departmentRepository;

    private DepartmentService departmentService;

    @Before
    public void setUp() {
        departmentService = new DepartmentServiceImpl(departmentRepository);
    }

    @Test
    public void createDepartment() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName(DEVELOP);
        departmentDto.setCreateDate(LocalDate.now());
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(ID);
        when(departmentRepository.save(any())).thenReturn(departmentEntity);

        Long departmentId = departmentService.createDepartment(departmentDto);

        assertEquals(ID, departmentId);
    }

    @Test
    public void createDepartmentWithLeadId() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName(DEVELOP);
        departmentDto.setCreateDate(LocalDate.now());
        departmentDto.setLeadId(LEAD_ID);
        DepartmentEntity departmentEntityOut = new DepartmentEntity();
        departmentEntityOut.setId(ID);
        DepartmentEntity leadDepartment = new DepartmentEntity();
        leadDepartment.setId(LEAD_ID);
        departmentEntityOut.setLeadDepartment(leadDepartment);
        DepartmentEntity departmentEntityIn = departmentDto.mapToEntity();
        departmentEntityIn.setLeadDepartment(leadDepartment);
        when(departmentRepository.findById(LEAD_ID)).thenReturn(Optional.of(leadDepartment));
        when(departmentRepository.save(departmentEntityIn)).thenReturn(departmentEntityOut);

        Long departmentId = departmentService.createDepartment(departmentDto);

        assertEquals(ID, departmentId);
    }
}
