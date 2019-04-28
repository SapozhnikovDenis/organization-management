package com.sapozhnikov.organizationmanagement.web.dto.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
@NoArgsConstructor
public class ChangeLeaderDepartmentRq {
    @NotNull
    @ApiModelProperty(value = "id department which change lead", example = "123456789", required = true)
    private Long departmentId;
    @NotNull
    @ApiModelProperty(value = "new id lead department", example = "987654321", required = true)
    private Long newLeadId;
}
