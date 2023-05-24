package org.bayasik.erik.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "Schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PatientId")
    private Patient patientId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PersonalId")
    private Personal personalId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchOfficeId")
    private BranchOffice branchOfficeId;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Price")
    private double price;
}
