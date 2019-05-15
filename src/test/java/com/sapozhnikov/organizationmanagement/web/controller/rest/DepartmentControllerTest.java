package com.sapozhnikov.organizationmanagement.web.controller.rest;

import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
public class DepartmentControllerTest {

    private static final String DEVELOP_DEPARTMENT_ID = "123456789";
    private static final String DEVELOP_DEPARTMENT_NAME = "develop";

    @Mock
    private DepartmentService departmentService;

    private MockMvc mockDepartmentController;

    @Before
    public void init(){
        mockDepartmentController =
                standaloneSetup(new DepartmentController(departmentService)).build();
    }

    @Test
    public void createDepartmentWithLead() throws Exception {
        String json = TestUtils.readFile("/json/request/department/createDepartmentWithLead.json");
        mockDepartmentController.perform(post("/api/v1/department")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createDepartment() throws Exception {
        when(departmentService.createDepartment(any())).thenReturn(1L);
        String json = TestUtils.readFile("/json/request/department/createDepartment.json");
        mockDepartmentController.perform(post("/api/v1/department")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createDepartmentNotValidJson() throws Exception {
        String json = TestUtils.readFile("/json/request/department/notValidCreateDepartment.json");
        mockDepartmentController.perform(post("/api/v1/department")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void renameDepartment() throws Exception {
        String json = TestUtils.readFile("/json/request/department/renameDepartment.json");
        mockDepartmentController.perform(put("/api/v1/department/rename")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void renameDepartmentNotValidJson() throws Exception {
        String json = TestUtils.readFile("/json/request/department/notValidRenameDepartment.json");
        mockDepartmentController.perform(put("/api/v1/department/rename")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteDepartment() throws Exception {
        String url = "/api/v1/department/{id}";
        mockDepartmentController.perform(delete(url, DEVELOP_DEPARTMENT_ID))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getDepartment() throws Exception {
        String url = "/api/v1/department/{id}";
        mockDepartmentController.perform(get(url, DEVELOP_DEPARTMENT_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getDirectSubordinatesDepartments() throws Exception {
        String url = "/api/v1/department/{id}/subordinate/direct";
        mockDepartmentController.perform(get(url, DEVELOP_DEPARTMENT_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getAllSubordinatesDepartments() throws Exception {
        String url = "/api/v1/department/{id}/subordinate/all";
        mockDepartmentController.perform(get(url, DEVELOP_DEPARTMENT_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void changeLeadersDepartment() throws Exception {
        String json = TestUtils.readFile("/json/request/department/changeLeadersDepartment.json");
        mockDepartmentController.perform(put("/api/v1/department/leader")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void changeLeadersDepartmentNotValidJson() throws Exception {
        String json = TestUtils.readFile("/json/request/department/notValidChangeLeadersDepartment.json");
        mockDepartmentController.perform(put("/api/v1/department" + "/leader")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getLeadDepartments() throws Exception {
        String url = "/api/v1/department/{id}/leader";
        mockDepartmentController.perform(get(url, DEVELOP_DEPARTMENT_ID))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getDepartmentByName() throws Exception {
        mockDepartmentController.perform(get("/api/v1/department")
                .param("name", DEVELOP_DEPARTMENT_NAME))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getSalaryFullDepartment() throws Exception {
        String url = "/api/v1/department/{id}/salary";
        mockDepartmentController.perform(get(url, DEVELOP_DEPARTMENT_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}