package com.sapozhnikov.organizationmanagement.web.controller.rest;

import com.sapozhnikov.organizationmanagement.web.dto.department.ChangeLeadersDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.CreateDepartmentRq;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetDepartmentRs;
import com.sapozhnikov.organizationmanagement.web.dto.department.RenameDepartmentRq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collections;
import java.util.List;

import static com.sapozhnikov.organizationmanagement.utils.Constant.*;

@Slf4j
@RestController
@RequestMapping(API_V_1_DEPARTMENTS_URL)
public class DepartmentController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createDepartment(@RequestBody @Valid CreateDepartmentRq createDepartmentRq) {
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = RENAME_URL, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> renameDepartment(@RequestBody @Valid RenameDepartmentRq renameDepartmentRq) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(ID_URL)
    public ResponseEntity<Void> removeDepartment(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = ID_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetDepartmentRs> getDepartment(@PathVariable Long id){
        return ResponseEntity.ok(new GetDepartmentRs());
    }

    @GetMapping(value = SUBORDINATES_URL + DIRECT_URL + ID_URL,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<GetDepartmentRs>> getDirectSubordinatesDepartments(@PathVariable Long id){
        return ResponseEntity.ok(Collections.singletonList(new GetDepartmentRs()));
    }

    @GetMapping(value = SUBORDINATES_URL + ALL_URL + ID_URL,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<GetDepartmentRs>> getAllSubordinatesDepartments(@PathVariable Long id){
        return ResponseEntity.ok(Collections.singletonList(new GetDepartmentRs()));
    }

    @PutMapping(value = LEADERS_URL, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> changeLeadersDepartment(
            @RequestBody @Valid ChangeLeadersDepartmentRq changeLeadersDepartmentRq) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = LEADERS_URL + ID_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<GetDepartmentRs>> getLeadDepartments(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
}
