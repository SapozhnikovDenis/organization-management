package com.sapozhnikov.organizationmanagement.web.dto.employee;

import com.sapozhnikov.organizationmanagement.web.dto.department.GetLeaderDepartmentRs;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetEmployeeRs extends GetLeaderDepartmentRs {
    @NotNull
    @ApiModelProperty(value = "this employee is lead in department", example = "true", required = true)
    private Boolean leadInDepartment;
}