package org.bayasik.erik.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bayasik.erik.models.Personal;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalVM {
    private int id;
    private String name;
    private String surname;
    private String address;
    private String email;
    private int branchOfficeId;
    private String post;
    private int salaryType;
    private int salaryPercent;
    private double salaryAmount;

    public PersonalVM(Personal personal) {
        this(personal.getId(),
                personal.getName(),
                personal.getSurname(),
                personal.getAddress(),
                personal.getEmail(),
                personal.getBranchOfficeId().getBranchOfficeId(),
                personal.getPost(),
                personal.getSalaryType(),
                personal.getSalaryPercent(),
                personal.getSalaryAmount());
    }

    public static ArrayList<PersonalVM> fromCollection(Collection<Personal> personals){
        var list = new ArrayList<PersonalVM>();
        for(var personal : personals) list.add(new PersonalVM(personal));

        return list;
    }
}
