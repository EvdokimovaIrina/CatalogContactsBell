package catalogContacts.dao.entityForJackson;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;


public class ContactJackson implements Serializable {
    @JacksonXmlProperty(localName = "id")
    private int number;
    @JacksonXmlProperty(localName = "name")
    private String fio;

    @JacksonXmlElementWrapper(localName = "contactDetailsList", useWrapping = false)
 //   @JacksonXmlProperty(localName = "contactDetailsList")
    private ContactDetailsListJackson contactDetailsList;
    @JacksonXmlElementWrapper(localName = "groupList", useWrapping = false)
  //  @JacksonXmlProperty(localName = "groupList")

    private GroupListJackson groupList;

    public ContactJackson() {

    }

    public ContactJackson(String fio) {
        this.fio = fio;
    }

    public ContactJackson(String fio, int number) {
        this.fio = fio;
        this.number = number;
    }


    public ContactJackson(String fio, int number, GroupListJackson groupList, ContactDetailsListJackson contactDetailsList) {
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

    public ContactDetailsListJackson getContactDetailsList() {
        return contactDetailsList;
    }

    public void setContactDetailsList(ContactDetailsListJackson contactDetailsList) {
        this.contactDetailsList = contactDetailsList;
    }

    public GroupListJackson getGroupList() {
        return groupList;
    }

    public void setGroupList(GroupListJackson groupList) {
        this.groupList = groupList;
    }
}
