package dev.nahidibrahimov.command;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        EWSService service = new EWSService();

        Command c1 = new AddMemberCommand("nahidibrahimovv@gmail.com", "Admins", service);
        Command c2 = new AddMemberCommand("test@gmail.com", "Users", service);

        MailTaskRunner.getInstance().addCommand(c1);
        MailTaskRunner.getInstance().addCommand(c2);

        Thread.sleep(3000);

        //MailTaskRunner.getInstance().shutdown();

    }
}