package catalogContacts.service;

import catalogContacts.event.Observable;
import catalogContacts.event.Observer;
import catalogContacts.model.Contact;
import catalogContacts.model.Group;
import catalogContacts.model.PhoneBook;
import catalogContacts.view.ViewOutputImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public class GroupServiceImpl implements GroupService, Observable {
    private static GroupServiceImpl instance;
    private List<Observer> ObserversList = new ArrayList<>();

    // Singleton
    private GroupServiceImpl() {
    }

    public static synchronized GroupService getInstance() {
        if (instance == null) {
            instance = new GroupServiceImpl();
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
            observer.handleEventChangedList(PhoneBook.groupsList);
        }
    }

    public void notifyObserverDetailslist() {
        for (Observer observer : ObserversList) {
            observer.handleEventDetailsList(PhoneBook.groupsList);
        }
    }

    ////////
    public void saveGroup(Group group) {
        if (!PhoneBook.groupsList.contains(group)) {
            //т.к. такой еще нет, то установим ему номер и добавим в список
            PhoneBook.groupsList.add(group);
            group.setNumber(PhoneBook.groupsList.indexOf(group) + 1);
        }
        notifyObserverChangedlist();
    }

    public void deleteGroup(Group group) {
        // найдем нужный контакт и удалим его
       /* Iterator<Group> iterContact = groupsList.iterator();
        while (iterContact.hasNext()) {
            Group group1 = iterContact.next();
            if (group1.equals(group1)) {
                iterContact.remove();
            }
        }*/
        PhoneBook.groupsList.remove(group);
        //после удаления контакта переберем список и изменим номера в контактах
        for (int i = 0; i < PhoneBook.groupsList.size(); i++) {
            Group group1 = PhoneBook.groupsList.get(i);
            group1.setNumber(i + 1);
        }
        notifyObserverChangedlist();
    }

    public void changeContactListAdd(Contact contact, List<Group> groupList) {
        //List<Contact> newListContact = new ArrayList<>();
        for (Group group : groupList) {
            List<Contact> contactListGroup = new ArrayList<>();
            contactListGroup.addAll(group.getContactList());
            if (!contactListGroup.contains(contact)) {
                contactListGroup.add(contact);
                group.setContactList(contactListGroup);
                int i = 0;
            }
        }

    }

    public void changeContactListDelete(Contact contact, List<Group> groupList) {

        for (Group group : groupList) {
            List<Contact> contactListGroup = new ArrayList<>();
            contactListGroup.addAll(group.getContactList());
            if (contactListGroup.contains(contact)) {
                contactListGroup.remove(contact);
                group.setContactList(contactListGroup);
            }
        }
    }

    public Group findByNumber(int number) {
        Group group = null;
        for (Group group2 : PhoneBook.groupsList) {
            if (group2.getNumber() == number) {
                group = group2;
            }
        }
        return group;
    }
}
