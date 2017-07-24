package catalogContacts.view.viewImpl;

import catalogContacts.model.*;
import catalogContacts.view.View;

import java.util.List;
import java.util.Map;

/**
 * Created by iren on 20.07.2017.
 */
public class ViewOutput extends View {

    @Override
    public void showListContact(List<Contact> contactList,Group group) {

        System.out.println("Список контактов " + ((group == null) ? "" : group.getName()) + ":");

        System.out.println("*************************");
        for (Contact contact : contactList) {
            showContact(contact);
        }
    }

    @Override
    public void showContact(Contact contact) {

        System.out.println(contact.getNumber() + " " + contact.getFio());
        for (ContactDetails contactDetails : contact.getContactDetailsList()) {
            System.out.println(contactDetails.getType() + ": " + contactDetails.getValue());
        }

        showGroupListContact(contact);
    }

    @Override
    public void showGroupListContact(Contact contact) {
        System.out.println("Группы:");
        for (Group group : contact.getGroupList()) {
            System.out.println(group.getNumber() + " " + group.getName());
        }
        System.out.println("*************************");
    }

    @Override
    public void showListGroup(List<Group> groupList) {
        System.out.println("Список групп:");
        for (Group group : groupList) {
            System.out.println(group.getNumber() + " " + group.getName());
        }
        System.out.println("*************************");
    }

    @Override
    public void showContactDetails(Contact contact, Map<TypeContact,String> mapDetails) {
        for (Map.Entry entry : mapDetails.entrySet()) {
            System.out.println( entry.getKey() + ": " + entry.getValue());
        }


    }

}
