package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.BranchOffice;
import org.bayasik.erik.models.Receptionist;
import org.bayasik.erik.viewmodels.ReceptionistVM;

public class AuthenticationCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.AUTHENTICATE)
    public void Authenticate(String login, String password) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        try {
            var receptionist = em.find(Receptionist.class, login);

            if (login.equals(receptionist.getLogin()) && password.equals(receptionist.getPassword())) {
                responser.jsonResponse(Commands.AUTHENTICATE, new ReceptionistVM(receptionist));
                System.out.println("AuthenticationSucc");
            } else {
                responser.jsonResponse(Commands.AUTHENTICATE, new ReceptionistVM( "", "", -1));
                System.out.println("AuthenticationFail");
            }
        }
        catch (Exception exception){
            responser.jsonResponse(Commands.AUTHENTICATE, new ReceptionistVM( "", "", -1));
            System.out.println("AuthenticationFail");
        }

        em.getTransaction().commit();
    }
}
