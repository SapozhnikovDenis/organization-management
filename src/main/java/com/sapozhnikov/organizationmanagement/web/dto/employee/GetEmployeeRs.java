package com.sapozhnikov.organizationmanagement.web.dto.employee;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class GetEmployeeRs {
    @NotNull
    @ApiModelProperty(value = "name", required = true)
    private NameRs name;
    @NotBlank
    @ApiModelProperty(value = "phone", example = "8(999)9999999", required = true)
    private String phone;
    @NotBlank
    @ApiModelProperty(value = "email", example = "karl@gmail.com", required = true)
    private String email;
    @NotBlank
    @ApiModelProperty(value = "email", example = "developer", required = true)
    private String position;
    @NotNull
    @ApiModelProperty(value = "this employee is lead in department", example = "true", required = true)
    private Boolean leadInDepartment;
}