package com.sapozhnikov.organizationmanagement.web.dto.department;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sapozhnikov.organizationmanagement.web.dto.employee.GetLeaderRs;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GetDepartmentRs {
    @NotBlank
    private String name;
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate createDate;
    @NotNull
    private GetLeaderRs leader;
    @NotNull
    private Long employeeCount;
}
