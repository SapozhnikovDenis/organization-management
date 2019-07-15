package com.sapozhnikov.organizationmanagement.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Audited
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "createDate")
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "leadDepartment_id")
    private DepartmentEntity leadDepartment;

    @ToString.Exclude
    @OneToMany(mappedBy = "leadDepartment")
    private List<DepartmentEntity> subordinatesDepartments;

    @ToString.Exclude
    @OneToMany(mappedBy = "department")
    private List<EmployeeEntity> employees;

    public DepartmentEntity() {
        this.subordinatesDepartments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }
}
