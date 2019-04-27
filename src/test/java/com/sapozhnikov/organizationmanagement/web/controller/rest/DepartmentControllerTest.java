package com.sapozhnikov.organizationmanagement.web.controller.rest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.sapozhnikov.organizationmanagement.utils.Constant.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class DepartmentControllerTest {

    private static final String DEPARTMENT_NAME = "develop";

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
        mockDepartmentController.perform(
                delete(API_V_1_DEPARTMENTS_URL + NAME_URL, DEPARTMENT_NAME))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getDepartment() throws Exception {
        mockDepartmentController.perform(
                get(API_V_1_DEPARTMENTS_URL + NAME_URL, DEPARTMENT_NAME))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getDirectSubordinatesDepartments() throws Exception {
        mockDepartmentController.perform(
                get(API_V_1_DEPARTMENTS_URL + NAME_URL + SUBORDINATES_URL + DIRECT_URL, DEPARTMENT_NAME))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getAllSubordinatesDepartments() throws Exception {
        mockDepartmentController.perform(
                get(API_V_1_DEPARTMENTS_URL + NAME_URL + SUBORDINATES_URL + ALL_URL, DEPARTMENT_NAME))
                .andExpect(status().is2xxSuccessful());
    }
}