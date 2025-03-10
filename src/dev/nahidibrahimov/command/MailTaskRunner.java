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
                        commands.wait();
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
        synchronized (commands) {
            commands.add(cmd);
            commands.notifyAll();
        }
    }

    public void shutdown() {
        stop = true;
        thread.interrupt();
    }

}
