package com.sapozhnikov.organizationmanagement.web.controller.rest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class EmployeeControllerTest {

    private static final String DEVELOP_DEPARTMENT_ID = "123456789";
    private static final String EMPLOYEE_ID = "987654321";

    private final MockMvc mockDepartmentController =
            standaloneSetup(new EmployeeController()).build();

    @Test
    public void getEmployeesInDepartment() throws Exception {
        mockDepartmentController.perform(get("/api/v1/employee")
                .param("departmentId", DEVELOP_DEPARTMENT_ID))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createEmployee() throws Exception {
        String json = TestUtils.readFile("/json/request/employee/employee.json");
        mockDepartmentController.perform(post("/api/v1/employee")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createEmployeeNotValidJson() throws Exception {
        String json = TestUtils.readFile("/json/request/employee/notValidEmployee.json");
        mockDepartmentController.perform(post("/api/v1/employee")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void changeEmployee() throws Exception {
        String json = TestUtils.readFile("/json/request/employee/employee.json");
        mockDepartmentController.perform(put("/api/v1/employee" + "/{id}", EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void changeEmployeeNotValidJson() throws Exception {
        String json = TestUtils.readFile("/json/request/employee/notValidEmployee.json");
        mockDepartmentController.perform(put("/api/v1/employee" + "/{id}", EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void dismissEmployee() throws Exception {
        String json = TestUtils.readFile("/json/request/employee/dismissEmployee.json");
        String url = "/api/v1/employee" + "/{id}" + "/dismiss";
        mockDepartmentController.perform(put(url, EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void dismissEmployeeNotValid() throws Exception {
        String json = TestUtils.readFile("/json/request/employee/notValidDismissEmployee.json");
        String url = "/api/v1/employee" + "/{id}" + "/dismiss";
        mockDepartmentController.perform(put(url, EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getEmployee() throws Exception {
        String url = "/api/v1/employee" + "/{id}";
        mockDepartmentController.perform(get(url, EMPLOYEE_ID))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void transferEmployeeToDepartment() throws Exception {
        String json = TestUtils.readFile("/json/request/employee/transferEmployeeToDepartment.json");
        mockDepartmentController.perform(put("/api/v1/employee" + "/transfer")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void transferEmployeeNotValidToDepartment() throws Exception {
        String json = TestUtils.readFile(
                "/json/request/employee/notValidTransferEmployeeToDepartment.json");
        mockDepartmentController.perform(put("/api/v1/employee" + "/transfer")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }
}