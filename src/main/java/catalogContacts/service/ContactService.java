package catalogContacts.service;

import catalogContacts.model.Contact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public class ContactService implements Serializable{
    List<Contact> contactsList = new ArrayList<>();

    public void saveContact(Contact contact) {
        if (contactsList.contains(contact)) {
            //т.к. контакт найден, то изменим его

        } else {
            //т.к. такого контакта еще нет, то установим ему номер и добавим в список
            contactsList.add(contact);
            contact.setNumber(contactsList.indexOf(contact)+1);
        }

    }

    public void deleteContact(Contact contact) {
        // найдем нужный контакт и удалим его
       Iterator<Contact> iterContact = contactsList.iterator();
        while (iterContact.hasNext()) {
            Contact contact1 = iterContact.next();
            if (contact1.equals(contact)) {
                iterContact.remove();
            }
        }
        //после удаления контакта переберем список и изменим номера в контактах
        for (int i = 0; i < contactsList.size(); i++) {
            Contact contact1 = contactsList.get(i);
            contact1.setNumber(i+1);
        }

    }

    public List<Contact> getContactsList() {
        return contactsList;
    }

}
