package catalogContacts.service;

import catalogContacts.model.Contact;
import catalogContacts.model.Group;

import java.util.List;

/**
 * Created by iren on 16.07.2017.
 */
public interface ContactService {

    void addContact(String name);
    void saveContact(Contact contact);
    void deleteContact(Contact contact);
    void showListContact(Contact contact, List<Group> groupsList);
    void changeGroupListAdd(Contact contact, List<Group> groupsList);
    void changeGroupListDelete(Contact contact, List<Group> groupsList);
}
