package org.bayasik.middleware;

import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.IChainOfResponsibility;

public class ConnectionIdSetter implements IChainOfResponsibility {
    @Override
    public void accept(ConnectionContext context, IChainOfResponsibility next) {
        context.put(Integer.class, context.hashCode());
        next.accept(context, next);
    }
}
