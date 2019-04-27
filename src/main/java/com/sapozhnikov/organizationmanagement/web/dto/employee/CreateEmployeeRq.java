package com.sapozhnikov.organizationmanagement.web.dto.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateEmployeeRq {
    @NotBlank
    private String firstName;
    @NotBlank
    private String secondName;
    private String middleName;
    @NotBlank
    private String sex;
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate employmentDate;
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateDismissal;
    @NotBlank
    private String position;
    @NotNull
    private BigDecimal salary;
    @NotNull
    private Boolean leadInDepartment;
}