package com.sapozhnikov.organizationmanagement.web.dto.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NotNull
public class DismissEmployeeRq {
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateDismissal;
}
