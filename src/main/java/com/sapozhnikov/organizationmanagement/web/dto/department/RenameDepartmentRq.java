package com.sapozhnikov.organizationmanagement.web.dto.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
@NoArgsConstructor
public class RenameDepartmentRq {
    @NotNull
    @ApiModelProperty(value = "id department", example = "123456789", required = true)
    private Long id;
    @NotBlank
    @ApiModelProperty(value = "new name department", example = "development", required = true)
    private String newName;
}
