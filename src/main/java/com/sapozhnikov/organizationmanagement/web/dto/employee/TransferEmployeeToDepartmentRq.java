package com.sapozhnikov.organizationmanagement.web.dto.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class TransferEmployeeToDepartmentRq {
    @NotNull
    private Long employeeId;
    @NotNull
    private Long transferToDepartmentId;
}
