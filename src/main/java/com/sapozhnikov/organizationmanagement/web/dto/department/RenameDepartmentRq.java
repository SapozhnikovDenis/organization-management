package com.sapozhnikov.organizationmanagement.web.dto.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
@NoArgsConstructor
public class RenameDepartmentRq {
    @NotBlank
    @ApiModelProperty(value = "id department", example = "123456789", required = true)
    private String id;
    @NotBlank
    @ApiModelProperty(value = "new name department", example = "development", required = true)
    private String newName;
}
