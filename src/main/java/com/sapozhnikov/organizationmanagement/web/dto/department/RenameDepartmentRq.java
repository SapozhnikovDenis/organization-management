package com.sapozhnikov.organizationmanagement.web.dto.department;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class RenameDepartmentRq {
    @NotBlank
    private String lastName;
    @NotBlank
    private String newName;
}
