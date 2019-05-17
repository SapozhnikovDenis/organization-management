package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.utils.exception.ApiException;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;
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
        CreateDepartmentRq createDepartmentRq = new CreateDepartmentRq();
        createDepartmentRq.setName(DEVELOP);
        createDepartmentRq.setCreateDate(LocalDate.now());
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(ID);
        when(departmentRepository.save(any())).thenReturn(departmentEntity);

        Long departmentId = departmentService.createDepartment(createDepartmentRq);

        assertEquals(ID, departmentId);
        verify(departmentRepository).save(any());
    }

    @Test
    public void createDepartmentWithLeadId() {
        CreateDepartmentRq createDepartmentRq = new CreateDepartmentRq();
        createDepartmentRq.setName(DEVELOP);
        createDepartmentRq.setCreateDate(LocalDate.now());
        createDepartmentRq.setLeadId(LEAD_ID);
        DepartmentEntity departmentEntityOut = new DepartmentEntity();
        departmentEntityOut.setId(ID);
        DepartmentEntity leadDepartment = new DepartmentEntity();
        leadDepartment.setId(LEAD_ID);
        departmentEntityOut.setLeadDepartment(leadDepartment);
        when(departmentRepository.findById(any())).thenReturn(Optional.of(leadDepartment));
        when(departmentRepository.save(any())).thenReturn(departmentEntityOut);

        Long departmentId = departmentService.createDepartment(createDepartmentRq);

        assertEquals(ID, departmentId);
        verify(departmentRepository).save(any());
    }

    @Test(expected = ApiException.class)
    public void renameDepartmentNotFoundDepartment() {
        when(departmentRepository.findById(any())).thenReturn(Optional.empty());

        departmentService.renameDepartment(new RenameDepartmentRq());
    }

    @Test
    public void renameDepartmentSuccessful() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        when(departmentRepository.findById(any())).thenReturn(Optional.of(departmentEntity));
        RenameDepartmentRq renameDepartmentRq = new RenameDepartmentRq();
        renameDepartmentRq.setNewName("name");

        departmentService.renameDepartment(renameDepartmentRq);

        assertEquals(renameDepartmentRq.getNewName(), departmentEntity.getName());
        verify(departmentRepository).save(departmentEntity);
    }

    @Test
    public void removeDepartmentSuccessful() {
        long id = 1L;
        when(departmentRepository.existsById(id)).thenReturn(true);

        departmentService.removeDepartment(id);

        verify(departmentRepository).deleteById(id);
    }

    @Test(expected = ApiException.class)
    public void removeDepartmentNotFoundDepartment() {
        long id = 1L;
        when(departmentRepository.existsById(id)).thenReturn(false);

        departmentService.removeDepartment(id);
    }
}
