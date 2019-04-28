package com.sapozhnikov.organizationmanagement.web.dto.employee;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
class NameRs {
    @NotBlank
    @ApiModelProperty(value = "first name", example = "Karl", required = true)
    private String first;
    @NotBlank
    @ApiModelProperty(value = "second name", example = "Karlov", required = true)
    private String second;
    @ApiModelProperty(value = "second name", example = "Karlovich")
    private String middle;
}
