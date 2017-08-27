package catalogContacts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Contact implements Serializable {
    private int number;
    private String fio;
    private User userByUserId;
    private List<ContactDetails> contactDetailsList = new ArrayList<>();
    private List<Group> groupList = new ArrayList<>();

    public Contact() {

    }

    public Contact(String fio) {
        this.fio = fio;
    }

    public Contact(String fio, int number) {
        this.fio = fio;
        this.number = number;
    }


    public Contact(String fio, int number, List<ContactDetails> contactDetailsList) {
        this.fio = fio;
        this.number = number;
        this.contactDetailsList = contactDetailsList;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="contact_id", nullable=false)
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic

    @Column(name = "contact_name", nullable=false)

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "contactByContactId", cascade = CascadeType.ALL,orphanRemoval=true)
    public List<ContactDetails> getContactDetailsList() {
        return contactDetailsList;
    }

    public void setContactDetailsList(List<ContactDetails> contactDetailsList) {
        this.contactDetailsList = contactDetailsList;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "contact_group", joinColumns = {
            @JoinColumn(name = "contact_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "group_id",
                    nullable = false, updatable = false) })
    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }
}
