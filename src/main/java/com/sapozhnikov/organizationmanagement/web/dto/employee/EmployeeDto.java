package com.sapozhnikov.organizationmanagement.web.dto.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ApiModel
@NoArgsConstructor
public class EmployeeDto {
    @ApiModelProperty(value = "id", example = "123456", required = true)
    private Long id;
    @NotBlank
    @ApiModelProperty(value = "first name", example = "Karl", required = true)
    private String firstName;
    @NotBlank
    @ApiModelProperty(value = "second name", example = "Karlov", required = true)
    private String secondName;
    @ApiModelProperty(value = "second name", example = "Karlovich")
    private String middleName;
    @NotBlank
    @ApiModelProperty(value = "sex", example = "male", required = true)
    private String sex;
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    @ApiModelProperty(value = "birth date", example = "20.12.2015", required = true)
    private LocalDate birthDate;
    @NotBlank
    @ApiModelProperty(value = "phone", example = "8(999)9999999", required = true)
    private String phone;
    @NotBlank
    @ApiModelProperty(value = "email", example = "karl@gmail.com", required = true)
    private String email;
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    @ApiModelProperty(value = "employment date", example = "20.12.2015", required = true)
    private LocalDate employmentDate;
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    @ApiModelProperty(value = "dismissal date", example = "20.12.2015", required = true)
    private LocalDate dismissalDate;
    @NotBlank
    @ApiModelProperty(value = "position", example = "developer", required = true)
    private String position;
    @NotNull
    @ApiModelProperty(value = "salary", example = "200000", required = true)
    private BigDecimal salary;
    @NotNull
    @ApiModelProperty(value = "lead in department", example = "true", required = true)
    private Boolean leadInDepartment;
}