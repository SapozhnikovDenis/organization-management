package com.sapozhnikov.organizationmanagement.web.controller.rest;

import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.GetDepartmentInfo;
import com.sapozhnikov.organizationmanagement.web.dto.department.*;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@Api
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @ApiOperation("create department")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Department successfully created"),
            @ApiResponse(code = 400, message = "Json not valid"),
            @ApiResponse(code = 415, message = "Service expect json")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createDepartment(@RequestBody @Valid @ApiParam("json create department")
                                                         CreateDepartmentRq createDepartmentRq) {
        Long idDepartment = departmentService.createDepartment(createDepartmentRq);
        URI uri = URI.create("/api/v1/department/" + idDepartment);
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation("rename department")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Department successfully renamed"),
            @ApiResponse(code = 400, message = "Json not valid"),
            @ApiResponse(code = 404, message = "Department not found"),
            @ApiResponse(code = 415, message = "Service expect json")
    })
    @PutMapping(value = "/rename", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> renameDepartment(@ApiParam(value = "json rename department", required = true)
                                                 @RequestBody @Valid RenameDepartmentRq renameDepartmentRq) {
        departmentService.renameDepartment(renameDepartmentRq);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("removed department")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Department successfully removed"),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeDepartment(@ApiParam(value = "id department", required = true)
                                                 @PathVariable @NotNull Long id) {
        departmentService.removeDepartment(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("get department by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Department successfully found", response = GetDepartmentRs.class),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GetDepartmentInfo getDepartment(@ApiParam(value = "id department", required = true)
                                           @PathVariable @NotNull Long id) {
        return departmentService.getDepartmentInfo(id);
    }

    @ApiOperation("get direct subordinates departments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Direct subordinates departments successfully found",
                    response = GetDepartmentRs.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @GetMapping(value = "/{id}/subordinate/direct", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GetDepartmentInfo> getDirectSubordinatesDepartments(@ApiParam(value = "id department", required = true)
                                                                    @PathVariable @NotNull Long id) {
        return departmentService.getDirectSubordinatesDepartments(id);
    }

    @ApiOperation("get all subordinates departments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All subordinates departments successfully found",
                    response = GetDepartmentRs.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @GetMapping(value = "/{id}/subordinate/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GetDepartmentInfo> getAllSubordinatesDepartments(@ApiParam(value = "id department", required = true)
                                                               @PathVariable @NotNull Long id) {
        return departmentService.getAllSubordinatesDepartments(id);
    }

    @ApiOperation("change leader department")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Leader department successfully change"),
            @ApiResponse(code = 400, message = "Json not valid"),
            @ApiResponse(code = 404, message = "Department not found"),
            @ApiResponse(code = 415, message = "Service expect json")
    })
    @PutMapping(value = "/leader", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> changeLeaderDepartment(@RequestBody @Valid
                                                       @ApiParam(value = "json change leader department", required = true)
                                                               ChangeLeaderDepartmentRq changeLeaderDepartmentRq) {
        departmentService.changeLeaderDepartment(changeLeaderDepartmentRq);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("get lead departments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lead departments successfully found",
                    response = GetDepartmentRs.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @GetMapping(value = "/{id}/leader", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<GetDepartmentRs>> getLeadDepartments(@PathVariable @NotNull
                                                                    @ApiParam(value = "id department", required = true)
                                                                            Long id) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("get department by name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Department successfully found", response = GetDepartmentRs.class),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetDepartmentRs> getDepartment(@PathParam("name") @NotNull
                                                         @ApiParam(value = "name department", required = true)
                                                                 String name) {
        return ResponseEntity.ok(new GetDepartmentRs());
    }

    @ApiOperation("get salary full department")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Salary department successfully found",
                    response = GetSalaryDepartmentRs.class),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @GetMapping(value = "/{id}/salary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetSalaryDepartmentRs> getSalaryFullDepartment(@PathVariable @NotNull
                                                                         @ApiParam(value = "id department", required = true)
                                                                                 Long id) {
        return ResponseEntity.ok(new GetSalaryDepartmentRs());
    }
}
