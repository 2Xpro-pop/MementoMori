package org.bayasik.erik.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PatientName")
    private String name;

    @Column(name = "PatientSurName")
    private String surname;

    @Column(name = "PatientNumber")
    private String phoneNumber;

    @Column(name = "PatientAddress")
    private String address;

    @Column(name = "PatientEmail")
    private String email;
}
