package com.sapozhnikov.organizationmanagement.web.dto.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel
@NoArgsConstructor
public class GetSalaryDepartmentRs {
    @NotNull
    @ApiModelProperty(value = "salary full department", example = "100000000000", required = true)
    private BigDecimal salary;
}
