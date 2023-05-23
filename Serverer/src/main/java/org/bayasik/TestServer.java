package org.bayasik;


import org.bayasik.commands.CommandsHandlersMiddleware;
import org.bayasik.commands.TestCommandHandlers;
import org.bayasik.messages.MessageMiddlewareHandler;
import org.bayasik.middleware.ConnectionIdSetter;
import org.bayasik.middleware.ConnectionLiverCheckerMiddleware;

public class TestServer {
    public static void main(String[] args) {
        var builder = GameServerBuilder.create();

        builder.setPort(1234);

        builder.addCommandHandler(TestCommandHandlers.class);

        builder.useOpen(ConnectionLiverCheckerMiddleware.class);
        builder.useOpen(ConnectionIdSetter.class);
        builder.useOpen(MessageMiddlewareHandler.class);
        builder.useOpen(CommandsHandlersMiddleware.class);

        var app = builder.build();

        app.start();
    }
}