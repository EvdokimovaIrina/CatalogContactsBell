package catalogContacts.model;

import javax.persistence.*;
import java.util.Collection;

/**
 *
 */
@Entity
@Table(name ="t_user")
public class User {
    private int id;
    private String login;
    private String password;
    private Collection<Contact> contactsByUserId;
    private Collection<Group> groupsByUserId;
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
    @Id
    @Column(name = "user_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "user_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userByUserId")
    public Collection<Contact> getContactsByUserId() {
        return contactsByUserId;
    }

    public void setContactsByUserId(Collection<Contact> contactsByUserId) {
        this.contactsByUserId = contactsByUserId;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userByUserId")
    public Collection<Group> getGroupsByUserId() {
        return groupsByUserId;
    }

    public void setGroupsByUserId(Collection<Group> groupsByUserId) {
        this.groupsByUserId = groupsByUserId;
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
