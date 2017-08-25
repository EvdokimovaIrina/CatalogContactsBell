package catalogContacts.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class ContactDetails implements Serializable {
    private int id;
    private TypeContact type;
    private String value;
    private Contact contactByContactId;

    public ContactDetails(int id,TypeContact type, String value){
        this.id = id;
        this.type = type;
        this.value = value;
    }
    public ContactDetails(TypeContact type, String value){
        this.type = type;
        this.value = value;
    }

    public ContactDetails() {
    }

    @Basic
    @Column(name = "details_type")
    public TypeContact getType() {
        return type;
    }

    public void setType(TypeContact type) {
        this.type = type;
    }

    @Basic
    @Column(name = "details_value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Id
    @Column(name="details_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "contact_id", referencedColumnName = "contact_id")
    public Contact getContactByContactId() {
        return contactByContactId;
    }

    public void setContactByContactId(Contact contactByContactId) {
        this.contactByContactId = contactByContactId;
    }
}
