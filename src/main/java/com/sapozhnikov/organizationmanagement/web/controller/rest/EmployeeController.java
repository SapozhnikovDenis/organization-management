package com.sapozhnikov.organizationmanagement.web.controller.rest;

import com.sapozhnikov.organizationmanagement.service.EmployeeService;
import com.sapozhnikov.organizationmanagement.web.dto.employee.DismissEmployeeRq;
import com.sapozhnikov.organizationmanagement.web.dto.employee.EmployeeDto;
import com.sapozhnikov.organizationmanagement.web.dto.employee.TransferEmployeeToDepartmentRq;
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
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @ApiOperation("get employees by department id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Employees successfully found",
                    response = EmployeeDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Employees not found"),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<EmployeeDto> getEmployeesInDepartment(@PathParam("departmentId") @NotNull
                                                                     @ApiParam(value = "id department", required = true)
                                                                               Long departmentId) {
        return employeeService.getEmployeesInDepartment(departmentId);
    }

    @ApiOperation("create employee")
    @ApiResponses({
            @ApiResponse(code = 201, message = "EmployeeDto successfully created"),
            @ApiResponse(code = 400, message = "Json not valid"),
            @ApiResponse(code = 415, message = "Service expect json")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createEmployee(@RequestBody @Valid
                                                   @ApiParam(value = "json create employee", required = true)
                                                       EmployeeDto employee) {
        URI uri = URI.create("/api/v1/employee" + "/123");
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation("change employee")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Json not valid"),
            @ApiResponse(code = 415, message = "Service expect json")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> changeEmployee(@RequestBody @ApiParam(value = "json change employee", required = true)
                                                       EmployeeDto employee,
                                               @PathVariable @ApiParam(value = "id employee", required = true)
                                                       Long id) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("dismiss employee")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Json not valid"),
            @ApiResponse(code = 415, message = "Service expect json")
    })
    @PutMapping(value = "/{id}/dismiss", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> dismissEmployee(@RequestBody @ApiParam(value = "json dismiss employee", required = true)
                                                            DismissEmployeeRq dismissEmployeeRq,
                                                @PathVariable @ApiParam(value = "id employee", required = true)
                                                        Long id) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("get employee by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Employees successfully found",
                    response = EmployeeDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Employees not found"),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable @ApiParam(value = "id employee", required = true) Long id) {
        return ResponseEntity.ok(new EmployeeDto());
    }

    @ApiOperation("transfer employee to department")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Json not valid"),
            @ApiResponse(code = 415, message = "Service expect json")
    })
    @PutMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> transferEmployeeToDepartment(
            @RequestBody @ApiParam(value = "json transfer employee", required = true)
                    TransferEmployeeToDepartmentRq transferEmployeeToDepartmentRq) {
        return ResponseEntity.ok().build();
    }
}
