package org.bayasik.erik.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bayasik.erik.models.Patient;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientVm {

    private int id;

    private String name;

    private String surname;

    private String phoneNumber;

    private String address;

    private String email;

    public PatientVm(Patient patient) {
        this(patient.getId(),
                patient.getName(),
                patient.getSurname(),
                patient.getPhoneNumber(),
                patient.getAddress(),
                patient.getEmail());
    }

    public static ArrayList<PatientVm> fromCollection(Collection<Patient> patients){
        var list = new ArrayList<PatientVm>();
        for(var patient : patients) list.add(new PatientVm(patient));

        return list;
    }

}
