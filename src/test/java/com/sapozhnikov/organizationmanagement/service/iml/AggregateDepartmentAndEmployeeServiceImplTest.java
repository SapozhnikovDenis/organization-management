package com.sapozhnikov.organizationmanagement.service.iml;

import com.sapozhnikov.organizationmanagement.service.AggregateDepartmentAndEmployeeService;
import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetDepartmentRs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AggregateDepartmentAndEmployeeServiceImplTest {

    @Mock
    private DepartmentService departmentService;

    private AggregateDepartmentAndEmployeeService aggregateDepartmentAndEmployeeService;

    @Before
    public void setUp() throws Exception {
        aggregateDepartmentAndEmployeeService = new AggregateDepartmentAndEmployeeServiceImpl(departmentService);
    }

    @Test
    public void getDepartmentWithEmployeeInfo() {
        long id = 1L;
        when(departmentService.getDepartmentInfo(id))
                .thenReturn(new GetDepartmentInfo());

        GetDepartmentRs departmentWithEmployeeInfo =
                aggregateDepartmentAndEmployeeService.getDepartmentWithEmployeeInfo(id);

        assertNotNull(departmentWithEmployeeInfo);
    }
}