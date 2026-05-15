package gmail;

import java.util.ArrayList;
import java.util.Scanner;

public class Gmail {
    private ArrayList<User> userList = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public void launchApplication() {
        while (true) {
            System.out.println("\n* * * * WELCOME TO GMAIL  * * * * ");
            System.out.println("1.CREATE ACCOUNT");
            System.out.println("2.LOGIN");
            System.out.println("3.EXIT");
            System.out.print("Enter your option: ");
            int option = sc.nextInt();
            switch (option) {
                case 1 -> createAccount();
                case 2 -> login();
                case 3 -> System.exit(0);
                default -> System.out.println("INVALID OPTION");
            }
        }
    }

    private void login() {
        System.out.println("\nLOGIN MODULE");
        System.out.print("Email Id: ");
        String userMail = sc.next();
        if (!(userMail.endsWith("@gmail.com"))) {
            userMail += "@gmail.com";
        }
        System.out.print("Password: ");
        String userPassword = sc.next();

        for (User user : userList) {
            if (userMail.equals(user.getMail()) && userPassword.equals(user.getPassword())) {
                homePage(user);
                return;
            }
        }
        System.out.println("INVALID CREDENTIALS");
    }

    private void createAccount() {
        System.out.println("\nCREATE ACCOUNT MODULE");
        System.out.print("First Name: ");
        String firstName = sc.next();
        System.out.print("Last Name: ");
        String lastName = sc.next();
        System.out.print("Contact: ");
        long contact = sc.nextLong();

        String mail;
        while (true) {
            System.out.print("Email: ");
            mail = sc.next();
            boolean exists = false;
            for (User u : userList) {
                if (mail.equals(u.getMail())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) break;
            System.out.println("MAIL ID ALREADY EXISTS. Try another.");
        }

        System.out.print("DOB: ");
        String dob = sc.next();
        System.out.print("Password: ");
        String password = sc.next();

        User newUser = new User(firstName + " " + lastName, contact, mail, dob, password);
        userList.add(newUser);
        System.out.println("Account created successfully!");
    }

    private void homePage(User user) {
        while (true) {
            System.out.println("\n**** HOME PAGE ****");
            System.out.println("1.Compose Mail");
            System.out.println("2.Draft");
            System.out.println("3.Sent Mails");
            System.out.println("4.Inbox");
            System.out.println("5.All Mails");
            System.out.println("6.Starred Mails");
            System.out.println("8.Send Draft Mails");
            System.out.println("8.Logout");
            System.out.print("Enter option: ");
            int option = sc.nextInt();
            switch (option) {
                case 1 -> composeMail(user);
                case 2 -> draftModule(user);
                case 3 -> sendModule(user);
                case 4 -> inboxModule(user);
                case 5 -> allMailModule(user);
                case 6 -> starredMailModule(user);
                case 7-> sendDraftMail(user);
                case 8 -> { return; }
                default -> System.out.println("INVALID OPTION");
            }
        }
    }

    private void composeMail(User user) {
        System.out.println("\nCOMPOSE MAIL");
        System.out.print("To: ");
        String toMail = sc.next();
        User toUser = null;
        for (User u : userList) {
            if (toMail.equals(u.getMail())) {
                toUser = u;
                break;
            }
        }
        if (toUser == null) {
            System.out.println("USER NOT FOUND");
            return;
        }
        sc.nextLine(); // consume newline
        System.out.print("Subject: ");
        String subject = sc.nextLine();
        System.out.print("Body: ");
        String body = sc.nextLine();

        Mail newMail = new Mail(user.getMail(), toUser.getMail(), subject, body);
        System.out.print("Send now? (YES/NO): ");
        String resp = sc.next();
        if (resp.equalsIgnoreCase("YES")) {
            user.sendMail(newMail);
            toUser.inboxMail(newMail);
            System.out.println("MAIL SENT SUCCESSFULLY");
        } else {
            user.draftMail(newMail);
            System.out.println("MAIL SAVED TO DRAFTS");
        }
    }

    private void inboxModule(User user) {
        System.out.println("\nINBOX");
        for (Mail m : user.getInboxMail()) {
            m.getMailInfo();
        }
    }

    private void sendModule(User user) {
        System.out.println("\nSENT MAILS");
        for (Mail m : user.getSendMail()) {
            m.getMailInfo();
        }
    }

    private void draftModule(User user) {
        System.out.println("\nDRAFTS");
        for (Mail m : user.getDraftMail()) {
            m.getMailInfo();
        }
    }
    private void sendDraftMail(User user) {
        ArrayList<Mail> draftList = user.getDraftMail();
        if (draftList.isEmpty()) {
            System.out.println("\nNO DRAFTS FOUND\n");
            return;
        }

        System.out.print("\nEnter the SRNO of the draft to send: ");
        int choice = sc.nextInt();
        if (choice < 1 || choice > draftList.size()) {
            System.out.println("INVALID CHOICE");
            return;
        }

        Mail draftToSend = draftList.get(choice - 1);

        // Find recipient user
        User toUser = null;
        for (User u : userList) {
            if (draftToSend.getReceiverMail().equals(u.getMail())) {
                toUser = u;
                break;
            }
        }

        if (toUser == null) {
            System.out.println("Recipient not found. Cannot send draft.");
            return;
        }

        // Move draft to sent + recipient inbox
        user.sendMail(draftToSend);
        toUser.inboxMail(draftToSend);
        draftList.remove(choice - 1);

        System.out.println("\nMAIL HAS BEEN SENT SUCCESSFULLY FROM DRAFT\n");
    }

    private void allMailModule(User user) {
        System.out.println("\nALL MAILS");
        inboxModule(user);
        sendModule(user);
        draftModule(user);
    }

    private void starredMailModule(User user) {
        System.out.println("\nSTARRED MAILS");
        for (Mail m : user.getStarredMail()) {
            m.getMailInfo();
        }
    }
}
