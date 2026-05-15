package gmail;

import java.util.ArrayList;

public class User {
    private String name;
    private long contact;
    private String mail;
    private String dob;
    private String password;
    private ArrayList<Mail> sendMail = new ArrayList<>();
    private ArrayList<Mail> inboxMail = new ArrayList<>();
    private ArrayList<Mail> starredMail = new ArrayList<>();
    private ArrayList<Mail> draftMail = new ArrayList<>();
    private ArrayList<Mail> binMail = new ArrayList<>();

    public User(String name, long contact, String mail, String dob, String password) {
        this.name = name;
        this.contact = contact;
        this.mail = mail;
        this.dob = dob;
        this.password = password;
    }

    public String getMail() { return mail; }
    public String getPassword() { return password; }
    public String getName() { return name; }

    public void sendMail(Mail send) { sendMail.add(send); }
    public void inboxMail(Mail inbox) { inboxMail.add(inbox); }
    public void draftMail(Mail draft) { draftMail.add(draft); }
    public void starredMail(Mail starred) { starredMail.add(starred); }
    public void binMail(Mail bin) { binMail.add(bin); }

    public ArrayList<Mail> getInboxMail() { return inboxMail; }
    public ArrayList<Mail> getSendMail() { return sendMail; }
    public ArrayList<Mail> getDraftMail() { return draftMail; }
    public ArrayList<Mail> getStarredMail() { return starredMail; }
    public ArrayList<Mail> getBinMail() { return binMail; }
}
