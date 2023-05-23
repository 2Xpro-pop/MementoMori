package org.bayasik.commands;

import com.google.inject.Inject;
import org.bayasik.connection.ChainOfResponsibilityDescriptor;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.IChainOfResponsibility;
import org.bayasik.messages.MessageThread;

public class CommandsHandlersMiddleware implements IChainOfResponsibility {
    private final CommandDescriptor[] descriptors;

    @Inject
    public CommandsHandlersMiddleware(CommandDescriptor[] descriptors) {
        this.descriptors = descriptors;
    }

    @Override
    public void accept(ConnectionContext context, IChainOfResponsibility next) {

        next.accept(context, next);

        var messageThread = context.get(MessageThread.class);

        messageThread.chainOfGettingMessage.add(new ChainOfResponsibilityDescriptor(
                CommandsHandlersPerMessageMiddleware.class)
        );

    }

}
