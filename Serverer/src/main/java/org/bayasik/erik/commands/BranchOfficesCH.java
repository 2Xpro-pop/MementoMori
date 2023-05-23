package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.BranchOffice;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class BranchOfficesCH implements CommandHandler {

    private EntityManager entityManager;
    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_BRANCH_OFFICE)
    public void add(String name, String address, double budget) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = new BranchOffice();
        office.setName(name);
        office.setAddress(address);
        office.setBudget(budget);
        System.out.println("AddBranchSucc");
        em.persist(office);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.ADD_BRANCH_OFFICE, office);
    }

    @Command(Commands.DELETE_BRANCH_OFFICE)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, id);
        System.out.println("DeleteBranchSucc");
        em.remove(office);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.DELETE_BRANCH_OFFICE, office);
    }

    @Command(Commands.UPDATE_BRANCH_OFFICE)
    public void update(int id, String name, String address, double budget) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, id);
        office.setName(name);
        office.setName(address);
        office.setBudget(budget);
        System.out.println("UpdateBranchSucc");
        em.merge(office);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.UPDATE_BRANCH_OFFICE, office);
    }

    @Command(Commands.GET_ALL_OFFICES)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var offices = em.createQuery("SELECT o FROM BranchOffice o", BranchOffice.class).getResultList();
        System.out.println("GetAllOfficesSucc");
        System.out.println(offices);
        responser.notifyResponse(Commands.GET_ALL_OFFICES, offices);
    }

    @Command(Commands.GET_OFFICE_BY_ID)
    public void getOfficeById(int id) {
        var em = DependencyLoader.getEntityManager();
        var offices = em.createQuery("SELECT o FROM BranchOffice o WHERE o.id = :branchOfficeId", BranchOffice.class).setParameter("branchOfficeId", id).getResultList();
        System.out.println("succ");
        System.out.println(offices);
        responser.notifyResponse(Commands.GET_OFFICE_BY_ID, offices);
    }
}
