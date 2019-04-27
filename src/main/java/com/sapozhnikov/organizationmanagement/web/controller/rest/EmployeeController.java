package com.sapozhnikov.organizationmanagement.web.controller.rest;

import com.sapozhnikov.organizationmanagement.web.dto.employee.DismissEmployeeRq;
import com.sapozhnikov.organizationmanagement.web.dto.employee.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;

import static com.sapozhnikov.organizationmanagement.utils.Constant.*;

@Slf4j
@RestController
@RequestMapping(API_V_1_EMPLOYEES_URL)
public class EmployeeController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Employee>> getEmployeesInDepartment(@PathParam(DEPARTMENT_ID_PARAM) Long departmentId) {
        return ResponseEntity.ok(Collections.singletonList(new Employee()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = ID_URL, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> changeEmployee(@RequestBody Employee employee,
                                               @PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = ID_URL + DISMISS_URL, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> dismissEmployee(@RequestBody DismissEmployeeRq dismissEmployeeRq,
                                               @PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = ID_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Employee>> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(Collections.singletonList(new Employee()));
    }
}
