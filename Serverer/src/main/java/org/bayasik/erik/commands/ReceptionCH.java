package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.BranchOffice;
import org.bayasik.erik.models.Receptionist;

public class ReceptionCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_RECEPTIONIST)
    public void AddReceptionist(String login, String password, int branchOfficeId) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, branchOfficeId);
        var receptionist = new Receptionist();
        receptionist.setLogin(login);
        receptionist.setPassword(password);
        receptionist.setBranchOfficeId(office);
        System.out.println("AddReceptionistSucc");
        em.persist(receptionist);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.ADD_RECEPTIONIST, receptionist);
    }

    @Command(Commands.GET_ALL_RECEPTIONISTS)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var receptionists = em.createQuery("SELECT o FROM Receptionist o", Receptionist.class).getResultList();
        System.out.println("GetAllReceptionistsSucc");
        responser.jsonResponse(Commands.GET_ALL_RECEPTIONISTS, receptionists);
    }
}
