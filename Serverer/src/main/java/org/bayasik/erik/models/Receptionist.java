package org.bayasik.erik.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Receptionist")
public class Receptionist {

    @Id
    @Column(name = "Login")
    private String login;

    @Column(name = "Password")
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "BranchOfficeId")
    private BranchOffice branchOfficeId;
}
