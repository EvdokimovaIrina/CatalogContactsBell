package catalogContacts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 */
@Entity
@Table(name ="t_user")
public class User implements Serializable{
    private int id;
    private String login;
    private String password;
    private List<Contact> contactsByUserId;
    private List<Group> groupsByUserId;

    public User() {
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable=false)
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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userByUserId", cascade = CascadeType.ALL,orphanRemoval=true)
    public List<Contact> getContactsByUserId() {
        return contactsByUserId;
    }

    public void setContactsByUserId(List<Contact> contactsByUserId) {
        this.contactsByUserId = contactsByUserId;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userByUserId", cascade = CascadeType.ALL,orphanRemoval=true)
    public List<Group> getGroupsByUserId() {
        return groupsByUserId;
    }

    public void setGroupsByUserId(List<Group> groupsByUserId) {
        this.groupsByUserId = groupsByUserId;
    }

}
