package dev.nahidibrahimov.command;

public class AddMemberCommand implements Command {

    private final String email;
    private final String group;
    private final EWSService receiver;

    public AddMemberCommand(String email, String group, EWSService receiver) {
        this.email = email;
        this.group = group;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.addMember(email, group);
    }
}
