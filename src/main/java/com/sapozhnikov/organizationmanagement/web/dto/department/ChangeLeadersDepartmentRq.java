package com.sapozhnikov.organizationmanagement.web.dto.department;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ChangeLeadersDepartmentRq {
    @NotNull
    private Long departmentId;
    @NotNull
    private Long leadId;
}
