package com.sapozhnikov.organizationmanagement.web.controller.rest;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static com.sapozhnikov.organizationmanagement.utils.Constants.API_V_1_DEPARTMENT_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class DepartmentControllerTest {

    private MockMvc mockDepartmentController;

    @Before
    public void setUp() {
        mockDepartmentController = standaloneSetup(new DepartmentController()).build();
    }

    @Test
    public void createDepartmentWithParentId() throws Exception {
        String json = IOUtils.toString(this.getClass()
                .getResourceAsStream("/json/request/department/createDepartmentWithParent.json"), StandardCharsets.UTF_8);

        mockDepartmentController.perform(post(API_V_1_DEPARTMENT_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createDepartment() throws Exception {
        String json = IOUtils.toString(this.getClass()
                .getResourceAsStream("/json/request/department/createDepartment.json"), StandardCharsets.UTF_8);

        mockDepartmentController.perform(post(API_V_1_DEPARTMENT_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createDepartmentNotValidJson() throws Exception {
        String json = IOUtils.toString(this.getClass()
                .getResourceAsStream("/json/request/department/notValidCreateDepartment.json"), StandardCharsets.UTF_8);

        mockDepartmentController.perform(post(API_V_1_DEPARTMENT_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createDepartmentWithNotMatchByContentType() throws Exception {
        String json = IOUtils.toString(this.getClass()
                .getResourceAsStream("/json/request/department/createDepartment.json"), StandardCharsets.UTF_8);

        mockDepartmentController.perform(post(API_V_1_DEPARTMENT_URL)
                .contentType(MediaType.APPLICATION_XML)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void renameDepartment() {
    }
}