package com.sapozhnikov.organizationmanagement.web.controller.rest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.sapozhnikov.organizationmanagement.utils.Constant.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class DepartmentControllerTest {

    private static final String DEVELOP_DEPARTMENT_NAME = "develop";
    private static final String QA_DEPARTMENT_NAME = "qa";

    private final MockMvc mockDepartmentController =
            standaloneSetup(new DepartmentController()).build();

    @Test
    public void createDepartmentWithParentId() throws Exception {
        String json = TestUtils.readFile("/json/request/department/createDepartmentWithParent.json");

        mockDepartmentController.perform(post(API_V_1_DEPARTMENTS_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createDepartment() throws Exception {
        String json = TestUtils.readFile("/json/request/department/createDepartment.json");

        mockDepartmentController.perform(post(API_V_1_DEPARTMENTS_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createDepartmentNotValidJson() throws Exception {
        String json = TestUtils.readFile("/json/request/department/notValidCreateDepartment.json");

        mockDepartmentController.perform(post(API_V_1_DEPARTMENTS_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void renameDepartment() throws Exception {
        String json = TestUtils.readFile("/json/request/department/renameDepartment.json");

        mockDepartmentController.perform(put(API_V_1_DEPARTMENTS_URL + RENAME_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void renameDepartmentNotValidJson() throws Exception {
        String json = TestUtils.readFile("/json/request/department/notValidRenameDepartment.json");

        mockDepartmentController.perform(put(API_V_1_DEPARTMENTS_URL + RENAME_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteDepartment() throws Exception {
        String url = API_V_1_DEPARTMENTS_URL + NAME_URL;
        mockDepartmentController.perform(delete(url, DEVELOP_DEPARTMENT_NAME))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getDepartment() throws Exception {
        String url = API_V_1_DEPARTMENTS_URL + NAME_URL;
        mockDepartmentController.perform(get(url, DEVELOP_DEPARTMENT_NAME))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getDirectSubordinatesDepartments() throws Exception {
        String url = API_V_1_DEPARTMENTS_URL + NAME_URL + SUBORDINATES_URL + DIRECT_URL;
        mockDepartmentController.perform(get(url, DEVELOP_DEPARTMENT_NAME))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getAllSubordinatesDepartments() throws Exception {
        String url = API_V_1_DEPARTMENTS_URL + NAME_URL + SUBORDINATES_URL + ALL_URL;
        mockDepartmentController.perform(get(url, DEVELOP_DEPARTMENT_NAME))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void changeSubordinatesDepartment() throws Exception {
        String url = API_V_1_DEPARTMENTS_URL + FIRST_NAME_URL + SUBORDINATES_URL + SECOND_NAME_URL;
        mockDepartmentController.perform(put(url, DEVELOP_DEPARTMENT_NAME, QA_DEPARTMENT_NAME))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getLeadDepartments() throws Exception {
        String url = API_V_1_DEPARTMENTS_URL + LEAD_URL + NAME_URL;
        mockDepartmentController.perform(get(url, DEVELOP_DEPARTMENT_NAME))
                .andExpect(status().is2xxSuccessful());
    }
}