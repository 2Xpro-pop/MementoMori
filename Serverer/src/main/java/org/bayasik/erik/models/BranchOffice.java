package org.bayasik.erik.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "BranchOffice")
public class BranchOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int branchOfficeId;

    @Column(name = "OfficeName")
    private String name;

    @Column(name = "OfficeAddress")
    private String address;

    @Column(name = "OfficeBudget")
    private double budget;

}
