package com.sapozhnikov.organizationmanagement.web.dto.department;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class GetSalaryDepartmentRs {
    @NotNull
    private Long salary;
}
