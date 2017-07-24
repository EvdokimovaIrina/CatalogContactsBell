package catalogContacts.controller;

import catalogContacts.model.TypeContact;
import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;

import java.util.Map;

/**
 * Created by iren on 24.07.2017.
 */
public interface Controller {
    void addContact(String name);

    void addGroup(String name);

    void addDetails(int number, Map<TypeContact, String> mapDetails);

    void addGroupToContact(int numberContact, int numberGroup);

    void deleteGroupToContact(int numberContact, int numberGroup);

    void showContactList(Integer numberGroup);

    void showDetails(Integer numberContact);

    void showGroupList();

    void deletContact(int numberContact);

    void deletContactDetails(int numberContact, int numberContactDetails);

    void ChangeSelectedContactDetails(int numberContact, int numberContactDetails, String value);

    void deletGroup(int numberGroup);

    void changeGroup(int numberGroup, String value);

    void changeContact(int numberContact, String value);

}
