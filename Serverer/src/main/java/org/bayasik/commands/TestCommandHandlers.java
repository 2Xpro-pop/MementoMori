package org.bayasik.commands;

import org.bayasik.Commands;
import org.bayasik.connection.ConnectionContext;

public class TestCommandHandlers implements CommandHandler {
    private ConnectionContext context;
    private int counter = 1;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
    }

    @Command(Commands.CREATE_ROOM)
    public void scopeTest(){
        System.out.println("connection id:"+context.get(Integer.class)+" counter:"+counter++);
    }
}
