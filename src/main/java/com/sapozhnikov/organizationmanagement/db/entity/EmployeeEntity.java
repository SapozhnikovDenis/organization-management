package com.sapozhnikov.organizationmanagement.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Audited
@NoArgsConstructor
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "second_name")
    private String secondName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "sex")
    private String sex;

    @NotNull
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotNull
    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "employment_date")
    private LocalDate employmentDate;

    @Column(name = "dismissal_date")
    private LocalDate dismissalDate;

    @NotNull
    @Column(name = "position")
    private String position;

    @NotNull
    @Column(name = "salary")
    private BigDecimal salary;

    @NotNull
    @Column(name = "lead_in_department")
    private Boolean leadInDepartment;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
}
