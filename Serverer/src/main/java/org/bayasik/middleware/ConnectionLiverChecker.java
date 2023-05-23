package org.bayasik.middleware;

import org.bayasik.connection.ConnectionContext;

public class ConnectionLiverChecker extends Thread {
    private double lastTime = System.currentTimeMillis();
    private final ConnectionContext context;

    public ConnectionLiverChecker(ConnectionContext context) {
        this.context = context;
    }

    @Override
    public void run(){
        while (true){
            try {
                Thread.sleep(15_000);
                if (System.currentTimeMillis() - lastTime > 50_000){
                    context.close();
                    break;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public double getLastTime(){
        return lastTime;
    }

    public void updateLastTime(){
        lastTime = System.currentTimeMillis();
    }

}
