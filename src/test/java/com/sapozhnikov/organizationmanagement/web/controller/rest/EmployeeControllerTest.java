package com.sapozhnikov.organizationmanagement.web.controller.rest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.sapozhnikov.organizationmanagement.utils.Constant.*;
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
        String url = API_V_1_EMPLOYEES_URL + DEPARTMENT_ID_URL;
        mockDepartmentController.perform(get(url, DEVELOP_DEPARTMENT_ID))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createEmployee() throws Exception {
        String json = TestUtils.readFile("/json/request/employee/employee.json");
        mockDepartmentController.perform(post(API_V_1_EMPLOYEES_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createEmployeeNotValidJson() throws Exception {
        String json = TestUtils.readFile("/json/request/employee/notValidEmployee.json");
        mockDepartmentController.perform(post(API_V_1_EMPLOYEES_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void changeEmployee() throws Exception {
        String json = TestUtils.readFile("/json/request/employee/employee.json");
        mockDepartmentController.perform(put(API_V_1_EMPLOYEES_URL + ID_URL, EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void changeEmployeeNotValidJson() throws Exception {
        String json = TestUtils.readFile("/json/request/employee/notValidEmployee.json");
        mockDepartmentController.perform(put(API_V_1_EMPLOYEES_URL + ID_URL, EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }
}