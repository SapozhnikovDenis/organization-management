package com.sapozhnikov.organizationmanagement.web.controller.rest;

import com.sapozhnikov.organizationmanagement.service.DepartmentService;
import com.sapozhnikov.organizationmanagement.service.dto.DepartmentDto;
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

import static com.sapozhnikov.organizationmanagement.utils.Constant.*;

@Api
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(API_V_1_DEPARTMENTS_URL)
public class DepartmentController {

    private final DepartmentService departmentService;

    @ApiOperation("create department")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Department successfully created"),
            @ApiResponse(code = 400, message = "Json not valid"),
            @ApiResponse(code = 409, message = "Fail create department"),
            @ApiResponse(code = 415, message = "Service expect json")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createDepartment(@RequestBody @Valid @ApiParam("json create department")
                                                             CreateDepartmentRq createDepartmentRq) {
        Long idDepartment = departmentService.createDepartment(DepartmentDto.of(createDepartmentRq));
        URI uri = URI.create(API_V_1_DEPARTMENTS_URL + "/" + idDepartment);
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation("rename department")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Department successfully renamed"),
            @ApiResponse(code = 400, message = "Json not valid"),
            @ApiResponse(code = 409, message = "Fail rename department"),
            @ApiResponse(code = 415, message = "Service expect json")
    })
    @PutMapping(value = RENAME_URL, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> renameDepartment(@RequestBody @Valid
                                                     @ApiParam(value = "json rename department", required = true)
                                                             RenameDepartmentRq renameDepartmentRq) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("removed department")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Department successfully removed"),
            @ApiResponse(code = 409, message = "Fail remove department"),
    })
    @DeleteMapping(ID_URL)
    public ResponseEntity<Void> removeDepartment(@PathVariable @NotNull
                                                     @ApiParam(value = "id department", required = true) Long id){
        return ResponseEntity.ok().build();
    }

    @ApiOperation("get department by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Department successfully found", response = GetDepartmentRs.class),
            @ApiResponse(code = 404, message = "Department not found"),
    })
    @GetMapping(value = ID_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetDepartmentRs> getDepartment(@PathVariable @NotNull
                                                         @ApiParam(value = "id department", required = true) Long id){
        return ResponseEntity.ok(new GetDepartmentRs());
    }

    @ApiOperation("get direct subordinates departments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Direct subordinates departments successfully found",
                    response = GetDepartmentRs.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @GetMapping(value = ID_URL + SUBORDINATES_URL + DIRECT_URL,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<GetDepartmentRs>> getDirectSubordinatesDepartments(@PathVariable @NotNull
                                                                     @ApiParam(value = "id department", required = true)
                                                                                              Long id){
        return ResponseEntity.ok(Collections.singletonList(new GetDepartmentRs()));
    }

    @ApiOperation("get all subordinates departments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All subordinates departments successfully found",
                    response = GetDepartmentRs.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @GetMapping(value = ID_URL + SUBORDINATES_URL + ALL_URL,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<GetDepartmentRs>> getAllSubordinatesDepartments(@PathVariable
                                                                    @ApiParam(value = "id department", required = true)
                                                                                           Long id){
        return ResponseEntity.ok(Collections.singletonList(new GetDepartmentRs()));
    }

    @ApiOperation("change leader department")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Leader department successfully change"),
            @ApiResponse(code = 400, message = "Json not valid"),
            @ApiResponse(code = 404, message = "Department not found"),
            @ApiResponse(code = 409, message = "Fail change leader department"),
            @ApiResponse(code = 415, message = "Service expect json")
    })
    @PutMapping(value = LEADERS_URL, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> changeLeaderDepartment(@RequestBody @Valid
                                                    @ApiParam(value = "json change leader department", required = true)
                                                                   ChangeLeaderDepartmentRq changeLeaderDepartmentRq) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("get lead departments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lead departments successfully found",
                    response = GetDepartmentRs.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @GetMapping(value = ID_URL + LEADERS_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    public ResponseEntity<GetDepartmentRs> getDepartment(@PathParam(NAME_PARAM) @NotNull
                                                             @ApiParam(value = "name department", required = true)
                                                                     String name){
        return ResponseEntity.ok(new GetDepartmentRs());
    }

    @ApiOperation("get salary full department")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Salary department successfully found",
                    response = GetSalaryDepartmentRs.class),
            @ApiResponse(code = 404, message = "Department not found")
    })
    @GetMapping(value = ID_URL + SALARY_URL,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetSalaryDepartmentRs> getSalaryFullDepartment(@PathVariable @NotNull
                                                                    @ApiParam(value = "id department", required = true)
                                                                                     Long id){
        return ResponseEntity.ok(new GetSalaryDepartmentRs());
    }
}
