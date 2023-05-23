package org.bayasik.erik.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bayasik.erik.models.Receptionist;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionistVM {

    private String login;
    private String password;
    private int branchOfficeId;

    public ReceptionistVM(Receptionist receptionist) {
        this(receptionist.getLogin(),
                receptionist.getPassword(),
                receptionist.getBranchOfficeId().getBranchOfficeId());
    }

    public static ArrayList<ReceptionistVM> fromCollection(Collection<Receptionist> receptionists){
        var list = new ArrayList<ReceptionistVM>();
        for(var receptionist : receptionists) list.add(new ReceptionistVM(receptionist));

        return list;
    }
}
