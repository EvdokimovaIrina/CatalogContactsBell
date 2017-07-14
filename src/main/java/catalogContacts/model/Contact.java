package catalogContacts.model;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    String fio;
    int number;
    List<Group> groupList = new ArrayList<>();
    List<ContactDetails> contactDetailsList = new ArrayList<>();

    public Contact(String fio) {
        this.fio = fio;
    }

    public Contact(String fio,int number) {
        this.fio = fio;
        this.number = number;
    }

    public Contact(String fio, int number, List<Group> groupList, List<ContactDetails> contactDetailsList) {
        this.fio = fio;
        this.number = number;
        this.groupList = groupList;
        this.contactDetailsList = contactDetailsList;

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
      }

    public List<Group> getGroup() {
        return groupList;
    }

    public List<ContactDetails> getContactDetailsList() {
        return contactDetailsList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public void setContactDetailsList(List<ContactDetails> contactDetailList) {
        this.contactDetailsList = contactDetailList;
    }
}
