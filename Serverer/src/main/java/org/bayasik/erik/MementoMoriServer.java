package org.bayasik.erik;

import org.bayasik.GameServerBuilder;
import org.bayasik.commands.CommandsHandlersMiddleware;
import org.bayasik.erik.commands.*;
import org.bayasik.messages.MessageMiddlewareHandler;
import org.bayasik.middleware.ConnectionIdSetter;
import org.bayasik.middleware.ConnectionLiverCheckerMiddleware;

public class MementoMoriServer {
    public static void main(String[] args) {
        var builder = GameServerBuilder.create();

        builder.setPort(6644);

        builder.useOpen(ConnectionIdSetter.class);
        builder.useOpen(MessageMiddlewareHandler.class);
        builder.useOpen(CommandsHandlersMiddleware.class);

        builder.addCommandHandler(AuthenticationCH.class);
        builder.addCommandHandler(BranchOfficesCH.class);
        builder.addCommandHandler(BudgetHistoryCH.class);
        builder.addCommandHandler(InventoryCH.class);
        builder.addCommandHandler(PatientsCH.class);
        builder.addCommandHandler(PersonalCH.class);
        builder.addCommandHandler(ScheduleCH.class);
        builder.addCommandHandler(ReceptionCH.class);

        var app = builder.build();

        app.start();
    }
}
