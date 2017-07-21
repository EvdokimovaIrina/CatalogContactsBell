package catalogContacts.service;

import catalogContacts.event.Observable;
import catalogContacts.event.Observer;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.model.PhoneBook;
import catalogContacts.view.ViewOutputImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by iren on 20.07.2017.
 */
public final class ContactServiceImpl implements ContactService, Observable {
    private static ContactServiceImpl instance;
    private List<Observer> ObserversList = new ArrayList<>();

    // Singleton
    private ContactServiceImpl() {
    }

    public static synchronized ContactService getInstance() {
        if (instance == null) {
            instance = new ContactServiceImpl();
            instance.addObserver(new ViewOutputImpl());
        }
        return instance;
    }
    //////

    //работа с наблюдателями
    public void addObserver(Observer observer) {
        if (!ObserversList.contains(observer)) {
            ObserversList.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        if (ObserversList.contains(observer)) {
            ObserversList.remove(observer);
        }
    }


    public void notifyObserverChangedlist() {
        for (Observer observer : ObserversList) {
            observer.handleEventChangedList(PhoneBook.contactsList);
        }
    }

    public void notifyObserverDetails() {
        for (Observer observer : ObserversList) {
            observer.handleEventDetailsList(PhoneBook.contactsList);
        }
    }

    ////////

    //
    public void addContact(String name) {
        Contact contact = new Contact(name);
        saveContact(contact);
    }

    //Сохранение контакта в списке
    public void saveContact(Contact contact) {

        if (!PhoneBook.contactsList.contains(contact)) {
            //т.к. такого контакта еще нет, то установим ему номер и добавим в список
            PhoneBook.contactsList.add(contact);
            contact.setNumber(PhoneBook.contactsList.indexOf(contact) + 1);
        }
        notifyObserverChangedlist();
    }

    //удаление контакта из списка и ссылок в ContactDetails и Group
    public void deleteContact(Contact contact) {
        // найдем нужный контакт и удалим его
        Iterator<Contact> iterContact = PhoneBook.contactsList.iterator();
        while (iterContact.hasNext()) {
            Contact contact1 = iterContact.next();
            if (contact1.equals(contact)) {
                iterContact.remove();
            }
        }

        //удалим ссылки на данный контакт в Group
        removeContactFromGroup(contact);
        contact.setGroupList(new ArrayList<>());

        //удалим ссылки на данный контакт в ContactDetails
        removeContactFromDetails(contact);
        contact.setContactDetailsList(new ArrayList<>());

        //после удаления контакта переберем список и изменим номера в контактах
        for (int i = 0; i < PhoneBook.contactsList.size(); i++) {
            Contact contact1 = PhoneBook.contactsList.get(i);
            contact1.setNumber(i + 1);
        }

        notifyObserverChangedlist();
    }

     public void showListContact(Contact contact, List<Group> groupsList) {
         notifyObserverChangedlist();
    }

    public void removeContactFromGroup(Contact contact) {
        for (Group group : contact.getGroup()) {
            Iterator<Contact> iterContact = group.getContactList().iterator();
            while (iterContact.hasNext()) {
                Contact contact1 = iterContact.next();
                if (contact1.equals(contact)) {
                    iterContact.remove();
                }
            }
        }
    }

    public void removeContactFromDetails(Contact contact) {
        for (ContactDetails contactDetails : contact.getContactDetailsList()) {
            contactDetails.setOwner(null);
        }
    }

    //Изменение состава GroupList добавлением переданного списка
    public void changeGroupListAdd(Contact contact, List<Group> groupList) {
        List<Group> newGroupList = new ArrayList<>();
        List<Group> groupListContact = contact.getGroupList();
        newGroupList.addAll(groupListContact);
        for (Group group : groupList) {
            if (!groupListContact.contains(group)) {
                newGroupList.add(group);
            }
        }
        contact.setGroupList(newGroupList);

        notifyObserverDetails();
    }

    //Изменение состава GroupList удаление переданного списка
    public void changeGroupListDelete(Contact contact, List<Group> groupList) {
        List<Group> newGroupList = new ArrayList<>();
        newGroupList.addAll(contact.getGroupList());
        newGroupList.removeAll(groupList);
        contact.setGroupList(newGroupList);

        notifyObserverDetails();
    }


}
