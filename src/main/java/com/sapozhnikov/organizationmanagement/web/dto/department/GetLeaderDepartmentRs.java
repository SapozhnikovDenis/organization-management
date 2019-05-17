package com.sapozhnikov.organizationmanagement.web.dto.department;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class GetLeaderDepartmentRs {
    @NotBlank
    @ApiModelProperty(value = "first name", example = "Karl", required = true)
    private String firstName;
    @NotBlank
    @ApiModelProperty(value = "second name", example = "Karlov", required = true)
    private String secondName;
    @ApiModelProperty(value = "second name", example = "Karlovich")
    private String middleName;
    @NotBlank
    @ApiModelProperty(value = "phone", example = "8(999)9999999", required = true)
    private String phone;
    @NotBlank
    @ApiModelProperty(value = "email", example = "karl@gmail.com", required = true)
    private String email;
    @NotBlank
    @ApiModelProperty(value = "position", example = "developer", required = true)
    private String position;
}
