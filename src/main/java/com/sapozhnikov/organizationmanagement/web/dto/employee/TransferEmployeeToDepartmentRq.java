package com.sapozhnikov.organizationmanagement.web.dto.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
@NoArgsConstructor
public class TransferEmployeeToDepartmentRq {
    @NotNull
    @ApiModelProperty(value = "employee id", example = "123456789", required = true)
    private Long employeeId;
    @NotNull
    @ApiModelProperty(value = "transfer to department id", example = "123456789", required = true)
    private Long transferToDepartmentId;
}
