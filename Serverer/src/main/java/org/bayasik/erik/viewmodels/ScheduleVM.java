package org.bayasik.erik.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bayasik.erik.models.BranchOffice;
import org.bayasik.erik.models.Patient;
import org.bayasik.erik.models.Schedule;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleVM {
    private int id;
    private PatientVm patient;
    private PersonalVM personal;
    private String date;
    private double price;
    private int branchOfficeId;

    public ScheduleVM(Schedule schedule) {
        this(schedule.getId(),
                new PatientVm(schedule.getPatientId()),
                new PersonalVM(schedule.getPersonalId()),
                schedule.getDate().toString(),
                schedule.getPrice(),
                schedule.getPersonalId().getBranchOfficeId().getBranchOfficeId());
    }

    public static ArrayList<ScheduleVM> fromCollection(Collection<Schedule> schedules){
        var list = new ArrayList<ScheduleVM>();
        for(var schedule : schedules) list.add(new ScheduleVM(schedule));

        return list;
    }
}
