package dev.nahidibrahimov.command;

import java.util.LinkedList;
import java.util.List;

public class MailTaskRunner implements Runnable {

    private final Thread thread;

    private final List<Command> commands;

    private volatile boolean stop;

    private static final MailTaskRunner RUNNER = new MailTaskRunner();

    public MailTaskRunner() {
        this.commands = new LinkedList<>();
        this.thread = new Thread(this);
        thread.start();
    }

    public static MailTaskRunner getInstance() {
        return RUNNER;
    }

    @Override
    public void run() {

        Command cmd = null;

        while (true) {

            synchronized (commands) {
                if (commands.isEmpty()) {
                    try {
                        System.out.println("inside empty check if");
                        System.out.println("run:" + thread.getState());
                        commands.wait();
                        System.out.println("changed to wait");
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted while waiting for commands");
                        if (stop) {
                            System.out.println("Stopped thread");
                            return;
                        }
                    }

                } else {
                    cmd = commands.remove(0);
                }
            }

            if (cmd != null) {
                cmd.execute();
            }

        }
    }

    public void addCommand(Command cmd) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        synchronized (commands) {
            System.out.println("adding command");
            System.out.println("addCommand:" + thread.getState());
            commands.add(cmd);
            commands.notifyAll();
        }
    }

    public void shutdown() {
        stop = true;
        thread.interrupt();
    }

}
