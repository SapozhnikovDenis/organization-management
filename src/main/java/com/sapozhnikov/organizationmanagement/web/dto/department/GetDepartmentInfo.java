package com.sapozhnikov.organizationmanagement.web.dto.department;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sapozhnikov.organizationmanagement.web.dto.department.GetLeaderDepartmentRs;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GetDepartmentInfo {
    @ApiModelProperty(value = "id department", example = "12345", required = true)
    private Long id;
    @NotBlank
    @ApiModelProperty(value = "name department", example = "development", required = true)
    private String name;
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    @ApiModelProperty(value = "create date department", example = "20.12.2015", required = true)
    private LocalDate createDate;
    @NotNull
    @Valid
    @ApiModelProperty(value = "leader", required = true)
    private GetLeaderDepartmentRs leader;
    @NotNull
    @ApiModelProperty(value = "employee count in department", example = "123", required = true)
    private Long employeeCount;
}
