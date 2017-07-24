package catalogContacts.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iren on 20.07.2017.
 */
public class PhoneBook implements Serializable {
    private List<Contact> contactsList = new ArrayList<>();
    private List<Group> groupsList = new ArrayList<>();
    public static PhoneBook instance;

    private PhoneBook(){

    }

    public static synchronized PhoneBook getPhoneBook(){
        if (instance == null) {
            instance = new PhoneBook();
        }

      return instance;
    }

    public List<Contact> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<Contact> contactsList) {
        this.contactsList = contactsList;
    }

    public List<Group> getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(List<Group> groupsList) {
        this.groupsList = groupsList;
    }


}
