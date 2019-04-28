package com.sapozhnikov.organizationmanagement.web.dto.department;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ApiModel
@NoArgsConstructor
public class CreateDepartmentRq {
    @ApiModelProperty(value = "id lead department", example = "123456789")
    private Long leadId;
    @NotBlank
    @ApiModelProperty(value = "name department", example = "123456789", required = true)
    private String name;
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    @ApiModelProperty(value = "create date department", example = "20.12.2015", required = true)
    private LocalDate createDate;
}
