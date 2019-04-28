package com.sapozhnikov.organizationmanagement.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Audited
@NoArgsConstructor
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "createDate")
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "id")
    private DepartmentEntity leadDepartment;

    @OneToMany(mappedBy = "lead")
    private List<DepartmentEntity> subordinatesDepartments;
}
