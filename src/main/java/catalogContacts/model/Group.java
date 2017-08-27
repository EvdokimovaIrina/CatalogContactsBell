package catalogContacts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by iren on 12.07.2017.
 */
@Entity
@Table(name = "t_group")
public class Group implements Serializable {
    private int number;
    private String name;
    private User UserByUserId;
    private List<Contact> contactList;

    public Group(){
    }

    public Group(String name){
        this.name = name;
    }

    public Group(String name,int number){
        this.name = name;
        this.number = number;
    }

    @Basic
    @Column(name = "group_name", nullable=false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="group_id", nullable=false)
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUserByUserId() {
        return UserByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        UserByUserId = userByUserId;
    }

   // @ManyToMany(mappedBy = "groupList", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   @JoinTable(name = "contact_group", joinColumns = {
           @JoinColumn(name = "group_id", nullable = false, updatable = false) },
           inverseJoinColumns = { @JoinColumn(name = "contact_id",
                   nullable = false, updatable = false) })
    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}
