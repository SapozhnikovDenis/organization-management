package com.sapozhnikov.organizationmanagement.web.dto.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class GetLeaderRs {
    @NotBlank
    private String firstName;
    @NotBlank
    private String secondName;
    private String middleName;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
    @NotBlank
    private String position;
}