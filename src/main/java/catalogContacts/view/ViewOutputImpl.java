package catalogContacts.view;

import catalogContacts.controller.Controller;
import catalogContacts.event.Observer;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.model.PhoneBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iren on 20.07.2017.
 */
public class ViewOutputImpl implements Observer {
    Controller controller;

    public void displayListContact(List<Contact> contactsList,Group group) {
        List<Contact> contactList = new ArrayList();
        if (group == null) {
            contactList = contactsList;
        } else {
            //contactList = group.getContactList();
        }

        System.out.println("Список контактов " + ((group == null) ? "" : group.getName()) + ":");

        System.out.println("*************************");
        for (Contact contact : contactList) {
            showContact(contact);
        }

    }

    public void showContact(Contact contact) {

        System.out.println(contact.getNumber() + " " + contact.getFio());
        for (ContactDetails contactDetails : contact.getContactDetailsList()) {
            System.out.println(contactDetails.getType() + ": " + contactDetails.getValue());
        }

        showGroupListContact(contact);
    }

    public void showGroupListContact(Contact contact) {
        System.out.println("Группы:");
        for (Group group : contact.getGroupList()) {
            System.out.println(group.getNumber() + " " + group.getName());
        }
        System.out.println("*************************");
    }

    public void displayListGroup(List<Group> groupList) {

    }

    public void handleEventDetailsList(Object object) {

    }

    public void handleEventChangedList(Object object) {
        if (object.equals(PhoneBook.contactsList)) {
            displayListContact(PhoneBook.contactsList,null);
        } else if (object.equals(PhoneBook.groupsList)) {
            displayListGroup(PhoneBook.groupsList);
        }
    }


}
