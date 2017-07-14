package catalogContacts.model;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    String fio;
    List<Group> groupList = new ArrayList<>();
    List<ContactDetails> contactDetailsList = new ArrayList<>();

    public Contact(String fio) {
        this.fio = fio;
    }

    public Contact(String fio, List<Group> groupList, List<ContactDetails> contactDetailsList) {
        this.fio = fio;
        this.groupList = groupList;
        this.contactDetailsList = contactDetailsList;
    }

    public String getFio() {
        return fio;
    }

    public List<Group> getGroup() {
        return groupList;
    }

    public List<ContactDetails> getContactDetailsList() {
        return contactDetailsList;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public void setContactDetailsList(List<ContactDetails> contactDetailList) {
        this.contactDetailsList = contactDetailList;
    }
}
