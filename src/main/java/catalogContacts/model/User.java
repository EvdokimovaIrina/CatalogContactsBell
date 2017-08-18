package catalogContacts.model;

/**
 *
 */
public class User {
    private int id;
    private String login;
    private String password;
    private int quantityContact;
    private int quantityGroup;

    public User() {
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password, int quantityContact, int quantityGroup) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.quantityContact = quantityContact;
        this.quantityGroup = quantityGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getQuantityContact() {
        return quantityContact;
    }

    public int getQuantityGroup() {
        return quantityGroup;
    }

    public void setQuantityContact(int quantityContact) {
        this.quantityContact = quantityContact;
    }

    public void setQuantityGroup(int quantityGroup) {
        this.quantityGroup = quantityGroup;
    }
}
