package catalogContacts.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 */
@Entity
@Table(name = "Contact_group")
public class ContactGroup implements Serializable {
    private Contact contactByContactId;
    private Group groupByGroupId;

    @ManyToOne
    @JoinColumn(name = "contact_id", referencedColumnName = "contact_id", nullable = false)
    public Contact getContactByContactId() {
        return contactByContactId;
    }

    public void setContactByContactId(Contact contactByContactId) {
        this.contactByContactId = contactByContactId;
    }

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false)
    public Group getGroupByGroupId() {
        return groupByGroupId;
    }

    public void setGroupByGroupId(Group groupByGroupId) {
        this.groupByGroupId = groupByGroupId;
    }


    private Integer id;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
