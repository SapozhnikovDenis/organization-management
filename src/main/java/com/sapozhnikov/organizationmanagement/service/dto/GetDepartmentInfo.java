package com.sapozhnikov.organizationmanagement.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GetDepartmentInfo {
    private String name;
    private LocalDate createDate;
}
