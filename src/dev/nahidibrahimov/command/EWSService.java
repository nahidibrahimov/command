package dev.nahidibrahimov.command;

public class EWSService {

    public void addMember(String contact, String contactGroup) {
        System.out.println("Added Member: " + contact + " to group: " + contactGroup);
    }

    public void removeMember(String contact, String contactGroup) {
        System.out.println("Removed Member: " + contact + " from group: " + contactGroup);
    }

}
