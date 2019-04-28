package com.sapozhnikov.organizationmanagement.web.dto.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ApiModel
@NotNull
public class DismissEmployeeRq {
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    @ApiModelProperty(value = "dismissal date", example = "20.12.2015", required = true)
    private LocalDate dismissalDate;
}
