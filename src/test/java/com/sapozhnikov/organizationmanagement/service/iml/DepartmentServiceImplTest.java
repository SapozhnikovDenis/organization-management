package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.db.entity.DepartmentEntity;
import com.sapozhnikov.organizationmanagement.db.repository.DepartmentRepository;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.utils.exception.ApiException;
import com.sapozhnikov.organizationmanagement.web.dto.department.ChangeLeaderDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        leadDepartment.setSubordinatesDepartments(new ArrayList<>());
        departmentEntityOut.setLeadDepartment(leadDepartment);
        when(departmentRepository.findById(LEAD_ID)).thenReturn(Optional.of(leadDepartment));
        when(departmentRepository.save(any())).thenReturn(departmentEntityOut);

        Long departmentId = departmentService.createDepartment(createDepartmentRq);

        assertEquals(ID, departmentId);
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

    @Test
    public void getDepartmentInfoSuccessful() {
        long id = 1L;
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setName(DEVELOP);
        departmentEntity.setCreateDate(LocalDate.now());
        when(departmentRepository.findById(id)).thenReturn(Optional.of(departmentEntity));

        GetDepartmentInfo getDepartmentInfo = departmentService.getDepartmentInfo(id);

        assertNotNull(getDepartmentInfo);
    }

    @Test(expected = ApiException.class)
    public void getDepartmentInfoNotFoundDepartment() {
        long id = 1L;
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        departmentService.getDepartmentInfo(id);
    }

    @Test
    public void getDirectSubordinatesDepartmentsSuccessful() {
        long id = 1L;
        DepartmentEntity departmentEntity = new DepartmentEntity();
        List<DepartmentEntity> subordinatesDepartments = Collections.singletonList(new DepartmentEntity());
        departmentEntity.setSubordinatesDepartments(subordinatesDepartments);
        when(departmentRepository.findById(id)).thenReturn(Optional.of(departmentEntity));

        List<GetDepartmentInfo> directSubordinatesDepartments =
                departmentService.getDirectSubordinatesDepartments(id);

        assertEquals(subordinatesDepartments.size(), directSubordinatesDepartments.size());
    }

    @Test(expected = ApiException.class)
    public void getDirectSubordinatesDepartmentsNotFoundDepartment() {
        long id = 1L;
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        departmentService.getDirectSubordinatesDepartments(id);
    }

    @Test
    public void getAllSubordinatesDepartments() {
        long id = 1L;
        List<DepartmentEntity> secondLevelSubordinatesDepartments =
                Collections.singletonList(new DepartmentEntity());
        DepartmentEntity firstLevelDepartment = new DepartmentEntity();
        firstLevelDepartment.setSubordinatesDepartments(secondLevelSubordinatesDepartments);
        List<DepartmentEntity> firstLevelSubordinatesDepartments =
                Collections.singletonList(firstLevelDepartment);
        DepartmentEntity targetDepartment = new DepartmentEntity();
        targetDepartment.setSubordinatesDepartments(firstLevelSubordinatesDepartments);
        when(departmentRepository.findById(id)).thenReturn(Optional.of(targetDepartment));

        List<GetDepartmentInfo> directSubordinatesDepartments =
                departmentService.getAllSubordinatesDepartments(id);

        int expectedSubordinatesDepartments = firstLevelSubordinatesDepartments.size() +
                secondLevelSubordinatesDepartments.size();
        assertEquals(expectedSubordinatesDepartments, directSubordinatesDepartments.size());
    }

    @Test
    public void getAllSubordinatesDepartmentsCheckUnique() {
        long id = 1L;
        DepartmentEntity duplicate = new DepartmentEntity();
        List<DepartmentEntity> secondLevelSubordinatesDepartments = Arrays.asList(new DepartmentEntity(), duplicate);
        DepartmentEntity firstLevelDepartment = new DepartmentEntity();
        firstLevelDepartment.setSubordinatesDepartments(secondLevelSubordinatesDepartments);
        List<DepartmentEntity> firstLevelSubordinatesDepartments = Arrays.asList(firstLevelDepartment, duplicate);
        DepartmentEntity targetDepartment = new DepartmentEntity();
        targetDepartment.setSubordinatesDepartments(firstLevelSubordinatesDepartments);
        when(departmentRepository.findById(id)).thenReturn(Optional.of(targetDepartment));

        List<GetDepartmentInfo> directSubordinatesDepartments =
                departmentService.getAllSubordinatesDepartments(id);

        Set<DepartmentEntity> uniqueDepartments = new HashSet<>();
        uniqueDepartments.addAll(firstLevelSubordinatesDepartments);
        uniqueDepartments.addAll(secondLevelSubordinatesDepartments);
        assertEquals(uniqueDepartments.size(), directSubordinatesDepartments.size());
    }

    @Test
    public void changeLeaderDepartmentSuccessful() {
        long departmentId = 1L;
        long leadDepartmentId = 2L;
        ChangeLeaderDepartmentRq changeLeaderDepartmentRq = new ChangeLeaderDepartmentRq();
        changeLeaderDepartmentRq.setDepartmentId(departmentId);
        changeLeaderDepartmentRq.setNewLeadId(leadDepartmentId);
        DepartmentEntity targetDepartment = new DepartmentEntity();
        DepartmentEntity oldLeadDepartment = new DepartmentEntity();
        targetDepartment.setLeadDepartment(oldLeadDepartment);
        oldLeadDepartment.setSubordinatesDepartments(new ArrayList<>());
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(targetDepartment));
        DepartmentEntity newLeadDepartment = new DepartmentEntity();
        newLeadDepartment.setSubordinatesDepartments(new ArrayList<>());
        when(departmentRepository.findById(leadDepartmentId)).thenReturn(Optional.of(newLeadDepartment));


        departmentService.changeLeaderDepartment(changeLeaderDepartmentRq);
    }

    @Test(expected = ApiException.class)
    public void changeLeaderDepartmentNotFoundDepartment() {
        long departmentId = 1L;
        long leadDepartmentId = 2L;
        ChangeLeaderDepartmentRq changeLeaderDepartmentRq = new ChangeLeaderDepartmentRq();
        changeLeaderDepartmentRq.setDepartmentId(departmentId);
        changeLeaderDepartmentRq.setNewLeadId(leadDepartmentId);
        long id = 1L;
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        departmentService.changeLeaderDepartment(changeLeaderDepartmentRq);
    }

    @Test(expected = ApiException.class)
    public void getLeadDepartmentsNotFoundDepartment() {
        long departmentId = 1L;
        DepartmentEntity department = new DepartmentEntity();
        department.setId(departmentId);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        departmentService.getLeadDepartments(departmentId);
    }

    @Test
    public void getLeadEmptyDepartments() {
        long departmentId = 1L;
        DepartmentEntity department = new DepartmentEntity();
        department.setId(departmentId);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        List<GetDepartmentInfo> leadDepartments = departmentService.getLeadDepartments(departmentId);

        assertTrue(leadDepartments.isEmpty());
    }

    @Test
    public void getLeadDepartments() {
        long departmentId = 1L;
        DepartmentEntity department = new DepartmentEntity();
        department.setId(departmentId);
        DepartmentEntity leadDepartment = new DepartmentEntity();
        leadDepartment.setId(2L);
        leadDepartment.setLeadDepartment(new DepartmentEntity());
        department.setLeadDepartment(leadDepartment);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        List<GetDepartmentInfo> leadDepartments = departmentService.getLeadDepartments(departmentId);

        assertEquals(2,leadDepartments.size());
    }

    @Test
    public void getLeadDepartmentsWithDuplicate() {
        long departmentId = 1L;
        DepartmentEntity department = new DepartmentEntity();
        department.setId(departmentId);
        DepartmentEntity leadDepartment = new DepartmentEntity();
        leadDepartment.setLeadDepartment(new DepartmentEntity());
        department.setLeadDepartment(leadDepartment);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        List<GetDepartmentInfo> leadDepartments = departmentService.getLeadDepartments(departmentId);

        assertEquals(2,leadDepartments.size());
    }

    @Test
    public void getDepartment() {
        when(departmentRepository.findByName(DEVELOP)).thenReturn(Optional.of(new DepartmentEntity()));

        GetDepartmentInfo department = departmentService.getDepartment(DEVELOP);

        assertNotNull(department);
    }

    @Test(expected = ApiException.class)
    public void getDepartmentNotFoundDepartment() {
        when(departmentRepository.findByName(DEVELOP)).thenReturn(Optional.empty());

        departmentService.getDepartment(DEVELOP);
    }
}
