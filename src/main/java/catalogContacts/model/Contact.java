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
    private List<ContactGroup> contactGroupsByContactId = new ArrayList<>();

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
    @Column(name="contact_id")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "contact_name")
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @OneToMany(mappedBy = "contactByContactId")
    public List<ContactGroup> getContactGroupsByContactId() {
        return contactGroupsByContactId;
    }

    public void setContactGroupsByContactId(List<ContactGroup> contactGroupsByContactId) {
        this.contactGroupsByContactId = contactGroupsByContactId;
    }

    @OneToMany(mappedBy = "contactByContactId")
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
}
