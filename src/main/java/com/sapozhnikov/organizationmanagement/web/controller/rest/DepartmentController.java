package com.sapozhnikov.organizationmanagement.web.controller.rest;

import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.sapozhnikov.organizationmanagement.utils.Constant.API_V_1_DEPARTMENT_URL;
import static com.sapozhnikov.organizationmanagement.utils.Constant.RENAME_URL;

@Slf4j
@RestController
@RequestMapping(API_V_1_DEPARTMENT_URL)
public class DepartmentController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createDepartment(@RequestBody @Valid CreateDepartmentRq createDepartmentRq) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = RENAME_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> renameDepartment(@RequestBody @Valid RenameDepartmentRq renameDepartmentRq) {
        return ResponseEntity.ok().build();
    }
}
