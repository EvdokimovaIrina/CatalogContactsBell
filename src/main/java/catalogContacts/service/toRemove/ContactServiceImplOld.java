package catalogContacts.service.toRemove;

import catalogContacts.model.Contact;
import catalogContacts.model.Group;
import catalogContacts.service.ContactService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public class ContactServiceImplOld {
    List<Contact> contactsList = new ArrayList<>();

    //Сохранение контакта в списке
    public void saveContact(Contact contact) {
        if (!contactsList.contains(contact)) {
            //т.к. такого контакта еще нет, то установим ему номер и добавим в список
            contactsList.add(contact);
            contact.setNumber(contactsList.indexOf(contact) + 1);
        }
    }

    //удаление контакта из списка и ссылок в ContactDetails и Group
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
            contact1.setNumber(i + 1);
        }

    }

    //Изменение состава GroupList добавлением переданного списка
    public void changeGroupListAdd(Contact contact, List<Group> groupList) {
        List<Group> newGroupList = new ArrayList<>();
        List<Group> groupListContact = contact.getGroupList();
        newGroupList.addAll(groupListContact);
        for (Group group: groupList) {
            if (!groupListContact.contains(group)){
                newGroupList.add(group);
            }
        }
        contact.setGroupList(newGroupList);
    }

    //Изменение состава GroupList удаление переданного списка
    public void changeGroupListDelete(Contact contact, List<Group> groupList) {
        List<Group> newGroupList = new ArrayList<>();
        newGroupList.addAll(contact.getGroupList());
        newGroupList.removeAll(groupList);
        contact.setGroupList(newGroupList);

    }

    public List<Contact> getContactsList() {
        return contactsList;
    }
}
