package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.BranchOffice;
import org.bayasik.erik.models.Personal;
import org.bayasik.erik.viewmodels.PersonalVM;

import java.util.Collection;

public class PersonalCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_PERSONAL)
    public void add(String name, String surname, String phoneNumber, String address, String email,
                    int branchOfficeId, String post, int salaryType, int salaryPercent, double salaryAmount) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, branchOfficeId);
        var personal = new Personal();

        personal.setName(name);
        personal.setSurname(surname);
        personal.setPhoneNumber(phoneNumber);
        personal.setAddress(address);
        personal.setEmail(email);
        personal.setBranchOfficeId(office);
        personal.setPost(post);
        personal.setSalaryType(salaryType);
        personal.setSalaryPercent(salaryPercent);
        personal.setSalaryAmount(salaryAmount);
        System.out.println("AddPersonalSucc");

        em.persist(personal);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.ADD_PERSONAL, personal);
    }

    @Command(Commands.DELETE_PERSONAL)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var personal = em.find(Personal.class, id);
        em.remove(personal);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.DELETE_PERSONAL, personal);
    }

    @Command(Commands.UPDATE_PERSONAL)
    public void update(int id, String name, String surname, String phoneNumber, String address, String email,
                       int branchOfficeId, String post, int salaryType, int salaryPercent, double salaryAmount) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, id);
        var personal = em.find(Personal.class, id);


        personal.setName(name);
        personal.setSurname(surname);
        personal.setPhoneNumber(phoneNumber);
        personal.setAddress(address);
        personal.setEmail(email);
        personal.setBranchOfficeId(office);
        personal.setPost(post);
        personal.setSalaryType(salaryType);
        personal.setSalaryPercent(salaryPercent);
        personal.setSalaryAmount(salaryAmount);

        em.merge(personal);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.UPDATE_PERSONAL, new PersonalVM(personal));
    }

    @Command(Commands.GET_ALL_PERSONAL)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var personals = em.createQuery("SELECT o FROM Personal o", Personal.class).getResultList();
        System.out.println(personals);
        responser.jsonResponse(Commands.GET_ALL_PERSONAL, PersonalVM.fromCollection(personals));
    }

    @Command(Commands.GET_PERSONAL_BY_ID)
    public void getPersonalById(int id) {
        var em = DependencyLoader.getEntityManager();
        var personal = em.createQuery("SELECT o FROM Personal o WHERE o.id = :personalId", Personal.class).setParameter("personalId", id).getResultList();
        System.out.println("GetPersonalBySucc");
        System.out.println(personal);
        responser.jsonResponse(Commands.GET_PERSONAL_BY_ID, PersonalVM.fromCollection(personal));
    }

    @Command(Commands.GET_PERSONAL_BY_BRANCH_OFFICE)
    public void getPersonalByBranchOffice(int branchOfficeId) {
        var em = DependencyLoader.getEntityManager();

        var branchOffice = new BranchOffice();
        branchOffice.setBranchOfficeId(branchOfficeId);

        var personal = em.createQuery("SELECT o FROM Personal o WHERE o.branchOfficeId = :branchOfficeId", Personal.class).setParameter("branchOfficeId", branchOffice).getResultList();
        System.out.println("GetPersonalBySucc");
        System.out.println(personal);
        responser.jsonResponse(Commands.GET_PERSONAL_BY_BRANCH_OFFICE, PersonalVM.fromCollection(personal));
    }
}