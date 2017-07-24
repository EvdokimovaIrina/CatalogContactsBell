package catalogContacts.service;

import catalogContacts.model.Contact;
import catalogContacts.model.TypeContact;
import java.util.Map;

/**
 * Created by iren on 16.07.2017.
 */
public interface ContactService {

    void addContact(String name);
    void addContactDetails(int numberContact,Map<TypeContact,String> mapDetails);
    void saveContact(Contact contact);
    void deleteContact(int numberContact);
    void deleteContactDetails(int numberContact,int numberContactDetails);
    void ChangeSelectedContactDetails(int numberContact,int numberContactDetails,String value);
    void showContactList(Integer numberGroup);
    void showContactDetails(int numberContact);
    void changeContact(int numberContact,String value);
    void addGroupToContact(int numberContact, int numberGroup);
    void deleteGroupToContact(int numberContact, int numberGroup);
}
