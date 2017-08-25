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
    private List<ContactGroup> contactGroupsByGroupId;

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
    @Column(name = "group_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name="group_id")
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

    @OneToMany(mappedBy = "groupByGroupId")
    public List<ContactGroup> getContactGroupsByGroupId() {
        return contactGroupsByGroupId;
    }

    public void setContactGroupsByGroupId(List<ContactGroup> contactGroupsByGroupId) {
        this.contactGroupsByGroupId = contactGroupsByGroupId;
    }
}
