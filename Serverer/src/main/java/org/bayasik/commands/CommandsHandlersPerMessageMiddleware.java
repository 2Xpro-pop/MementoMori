package org.bayasik.commands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.IChainOfResponsibility;
import org.bayasik.messages.MessageContext;

public class CommandsHandlersPerMessageMiddleware implements IChainOfResponsibility {
    private final CommandDescriptor[] descriptors;
    private final MessageContext messageContext;
    private final Injector injector;

    @Inject
    public CommandsHandlersPerMessageMiddleware(CommandDescriptor[] descriptors, MessageContext messageContext, Injector injector) {
        this.descriptors = descriptors;
        this.messageContext = messageContext;
        this.injector = injector;
    }

    @Override
    public void accept(ConnectionContext context, IChainOfResponsibility next) {
        var command = new CommandsExplorer(descriptors, context);
        var cmd = command.readCommand(messageContext.lastMessage());

        activateCommandHandler(cmd, context);
        command.InvokeCommand(cmd);

        next.accept(context, next);

    }

    private void activateCommandHandler(CommandToken cmd, ConnectionContext context) {
        var id = context.get(Integer.class);
        for(var descriptor : descriptors) {
            if(descriptor.isAnonymous()) continue;

            descriptor.setInstance(null);
        }
        for(var descriptor : descriptors) {
            if(descriptor.isAnonymous() || descriptor.commandId != cmd.commandId()) continue;

            if(descriptor.getInstance() == null){
                var instance = (CommandHandler)injector.getInstance(descriptor.method.getDeclaringClass());
                instance.setContext(context);
                descriptor.setInstance(instance);
            }

            break;

        }
    }
}