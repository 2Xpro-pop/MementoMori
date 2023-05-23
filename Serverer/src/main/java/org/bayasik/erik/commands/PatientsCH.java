package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.BranchOffice;
import org.bayasik.erik.models.Patient;

public class PatientsCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_PATIENT)
    public void add(String name, String surname, String phoneNumber, String address, String email) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var patient = new Patient();
        patient.setName(name);
        patient.setSurname(surname);
        patient.setPhoneNumber(phoneNumber);
        patient.setAddress(address);
        patient.setEmail(email);
        System.out.println("AddPatientSucc");
        em.persist(patient);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.ADD_PATIENT, patient);
    }

    @Command(Commands.DELETE_PATIENT)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var patient = em.find(Patient.class, id);
        em.remove(patient);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.DELETE_PATIENT, patient);
    }

    @Command(Commands.UPDATE_PATIENT)
    public void update(int id, String name, String surname, String phoneNumber, String address, String email) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var patient = em.find(Patient.class, id);
        patient.setName(name);
        patient.setSurname(surname);
        patient.setPhoneNumber(phoneNumber);
        patient.setAddress(address);
        patient.setEmail(email);
        em.merge(patient);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.UPDATE_PATIENT, patient);
    }

    @Command(Commands.GET_ALL_PATIENT)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var patients = em.createQuery("SELECT o FROM Patient o", Patient.class).getResultList();
        System.out.println(patients);
        responser.jsonResponse(Commands.GET_ALL_PATIENT, patients);
    }

    @Command(Commands.GET_PATIENT_BY_ID)
    public void getPatientById(int id) {
        var em = DependencyLoader.getEntityManager();
        var patients = em.createQuery("SELECT o FROM Patient o WHERE o.id = :patientId", Patient.class).setParameter("patientId", id).getResultList();
        System.out.println("GetPatientByIdSucc");
        System.out.println(patients);
        responser.jsonResponse(Commands.GET_PATIENT_BY_ID, patients);
    }
}