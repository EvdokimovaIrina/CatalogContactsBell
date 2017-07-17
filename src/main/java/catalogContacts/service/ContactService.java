package catalogContacts.service;

import catalogContacts.model.Contact;
import catalogContacts.model.Group;

import java.util.List;

/**
 * Created by iren on 16.07.2017.
 */
public interface ContactService {
    public void saveContact(Contact contact);
    public void deleteContact(Contact contact);
    public List<Contact> getContactsList();
    public void changeGroupListAdd(Contact contact, List<Group> groupsList);
    public void changeGroupListDelete(Contact contact, List<Group> groupsList);
}
