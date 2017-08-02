package catalogContacts.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Contact implements Serializable {

    private int number;
    private String fio;
    private List<Group> groupList = new ArrayList<>();
    private List<ContactDetails> contactDetailsList = new ArrayList<>();
    public Contact() {

    }

    public Contact(String fio) {
        this.fio = fio;
    }

    public Contact(String fio, int number) {
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

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public List<ContactDetails> getContactDetailsList() {
        return contactDetailsList;
    }

    public void setContactDetailsList(List<ContactDetails> contactDetailsList) {
        this.contactDetailsList = contactDetailsList;
    }
}
