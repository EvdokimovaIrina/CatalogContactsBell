package catalogContacts.service;

import catalogContacts.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public class ContactService {
    List<Contact> contactsList = new ArrayList<>();
    public void saveContact(Contact contact){
        contactsList.add(contact);

    }

    public List<Contact> getContactsList() {
        return contactsList;
    }

 }
