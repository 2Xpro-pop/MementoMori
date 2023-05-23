package org.bayasik.erik.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Personal")
public class Personal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PersonalName")
    private String name;

    @Column(name = "PersonalSurName")
    private String surname;

    @Column(name = "PersonalNumber")
    private String phoneNumber;

    @Column(name = "PersonalAddress")
    private String address;

    @Column(name = "PersonalEmail")
    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "BranchOfficeId")
    private BranchOffice branchOfficeId;

    @Column(name = "Post")
    private String post;

    @Column(name = "SalaryType")
    private int salaryType;

    @Column(name = "SalaryPercent")
    private int salaryPercent;

    @Column(name = "SalaryAmount")
    private double salaryAmount;
}
