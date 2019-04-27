package com.sapozhnikov.organizationmanagement.web.controller.rest;

import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.sapozhnikov.organizationmanagement.utils.Constant.*;

@Slf4j
@RestController
@RequestMapping(API_V_1_DEPARTMENTS_URL)
public class DepartmentController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createDepartment(@RequestBody @Valid CreateDepartmentRq createDepartmentRq) {
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = RENAME_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> renameDepartment(@RequestBody @Valid RenameDepartmentRq renameDepartmentRq) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = ID_URL)
    public ResponseEntity<Void> removeDepartment(@PathVariable String id){
        return ResponseEntity.ok().build();
    }
}
