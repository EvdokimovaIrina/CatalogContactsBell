package catalogContacts.view;

import catalogContacts.event.Event;
import catalogContacts.event.Observer;
import catalogContacts.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by iren on 23.07.2017.
 */
public abstract class View implements Observer {

    public void handleEvent(Event event) {
        //  showContactDetails
        switch (event.getEventType()) {
            case showContactList:
                showListContact((List<Contact>) event.getMainObject(), null);
                break;
            case showContactListFromGroup:
                showListContact((List<Contact>) event.getMainObject(), (Group) event.getValue());
                break;
            case showGroupList:
               // showListGroup(PhoneBook.getPhoneBook().getGroupsList());
                break;
            case showContactDetails:
                showContactDetails((Contact) event.getMainObject(), (Map) event.getValue());
                break;
            case showContactData:
                if (event.getMainObject().getClass().getName() == "Contact") {
                    showContact((Contact) event.getMainObject());
                } else {
                    System.out.println("Ошибка получения данных контакта");
                }
                break;
            case errorNumber:
                System.out.println("Номер за пределами границ");
                break;
        }
    }

    public void showListContact(List<Contact> contactList, Group group) {

    }

    public void showListGroup(List<Group> groupList) {

    }

    public void showContact(Contact contact) {

    }

    public void showGroupListContact(Contact contact) {

    }

    public void showContactDetails(Contact contact, Map<TypeContact, String> mapDetails) {

    }

    /////////////
    public boolean action(int selectedAction) throws IOException {
        return false;
    }

    public void addContact() throws IOException {
    }

    public void addGroup() throws IOException{}

    public void showContactList(){}

    public void showContactListFromGroup() throws IOException{}

    public void showGroupList(){}

    public void changeContact()throws IOException{}

    public void showMenuContact(int numberContact){}

    public boolean actionMenuContact(int selectedAction, int numberContact) throws IOException{
        return false;
    }

    public void changeContactName(int numberContact) throws IOException{}

    public void AddContactDetails(int numberContact) throws IOException{}

    public void selecteActionGroupListContact(int numberContact) throws IOException{}

    public void changeGroupListForContact(int numberContact, boolean delete) throws IOException{}

    public void changeGroup() throws IOException{}

    public void deletContact() throws IOException{}

    public void deletGroup() throws IOException{}

    public void showMenuContactDetails(int numberContact) throws IOException{}

    public void deleteContactDetails(int numberContact) throws IOException{}

    public void ChangeSelectedContactDetails(int numberContact) throws IOException{}






}
