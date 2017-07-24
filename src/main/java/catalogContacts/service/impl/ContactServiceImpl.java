package catalogContacts.service.impl;

import catalogContacts.event.Event;
import catalogContacts.event.Observer;
import catalogContacts.event.TypeEvent;
import catalogContacts.model.*;
import catalogContacts.service.ContactService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by iren on 20.07.2017.
 */
public final class ContactServiceImpl implements ContactService, Observer.Observable {
    private static ContactServiceImpl instance;
    private List<Contact> contactList;
    private List<Group> groupList;
    private List<Observer> ObserversList = new ArrayList<>();

    // Singleton
    private ContactServiceImpl() {
        this.contactList = PhoneBook.getPhoneBook().getContactsList();
        this.groupList = PhoneBook.getPhoneBook().getGroupsList();
    }

    public static synchronized ContactServiceImpl getInstance() {
        if (instance == null) {
            instance = new ContactServiceImpl();
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


    public void notifyObserver(TypeEvent typeEvent, Object mainObject, Object value) {
        for (Observer observer : ObserversList) {
            observer.handleEvent(new Event(typeEvent, mainObject,value));
        }
    }

    ////////

    //
    //добавление контакта
    public void addContact(String name) {
        Contact contact = new Contact(name);
        contact.setContactDetailsList(new ArrayList<>());
        saveContact(contact);
    }

    public void addContactDetails(int numberContact,Map<TypeContact, String> mapDetails) {
        if (numberWithinBorders(numberContact, contactList.size())){
            Contact contact = contactList.get(numberContact);
            for (Map.Entry entry : mapDetails.entrySet()) {
                ContactDetails contactDetails = new ContactDetails((TypeContact) entry.getKey(),(String) entry.getValue());
                contact.getContactDetailsList().add(contactDetails);
            }
            notifyObserver(TypeEvent.showContactDetails,contact,mapDetails);

        }
    }

    //Сохранение контакта в списке
    public void saveContact(Contact contact) {
        if (!contactList.contains(contact)) {
            //т.к. такого контакта еще нет, то установим ему номер и добавим в список
            contactList.add(contact);
            contact.setNumber(contactList.indexOf(contact) + 1);
        }
        notifyObserver(TypeEvent.showContactList, contactList,null);
    }

    //удаление контакта из списка
    public void deleteContact(int numberContact) {
        // если номер в пределах, то удалим контакт с выбранным номером
        if (numberWithinBorders(numberContact, contactList.size())){
           contactList.remove(numberContact);
        }

        //после удаления контакта переберем список и изменим номера в контактах
        for (int i = 0; i < contactList.size(); i++) {
            Contact contact1 = contactList.get(i);
            contact1.setNumber(i + 1);
        }

        notifyObserver(TypeEvent.showContactList, contactList,null);
    }

    public void deleteContactDetails(int numberContact, int numberContactDetails) {
        Contact contact = getContactByNumber(numberContact);
        if (!(contact==null)){
            List<ContactDetails> contactDetailsList = contact.getContactDetailsList();
            if(numberContactDetails>=0&numberContactDetails<contactDetailsList.size()){
                contactDetailsList.remove(numberContactDetails);
            }else {
                notifyObserver(TypeEvent.errorNumber,null,null);
            }
        }
    }

    public void ChangeSelectedContactDetails(int numberContact, int numberContactDetails,String value) {
        Contact contact = getContactByNumber(numberContact);
        if (!(contact==null)){
            List<ContactDetails> contactDetailsList = contact.getContactDetailsList();
            if(numberContactDetails>=0&numberContactDetails<contactDetailsList.size()){
                contactDetailsList.get(numberContactDetails).setValue(value);
            }else {
                notifyObserver(TypeEvent.errorNumber,null,null);
            }
        }
    }

    public void showContactList(Integer numberGroup) {
        if (numberGroup == null) {
            notifyObserver(TypeEvent.showContactList, contactList,null);
        } else {
            Group group = GroupServiceImpl.getInstance().findByNumber(numberGroup);
            notifyObserver(TypeEvent.showContactListFromGroup, contactListFromGroup(group),group);
        }

    }

    public List<Contact> contactListFromGroup(Group group) {
        List<Contact> list = new ArrayList<>();
        for (Contact contact : contactList) {
            if (contact.getGroupList().contains(group)) {
                list.add(contact);
            }
        }
        return list;
    }


    public Contact getContactByNumber(int numberContact){
        Contact contact = null;
        if (numberWithinBorders(numberContact, contactList.size())) {
            contact = contactList.get(numberContact);
        }
        return contact;
    }

    public void showContactDetails(int numberContact) {
        Contact contact = getContactByNumber(numberContact);
        if (!(contact ==null)){
            notifyObserver(TypeEvent.showContactData,contact,null);
        }

    }

    public void changeContact(int numberContact,String value) {
        Contact contact = getContactByNumber(numberContact);
        if (!(contact ==null)){
            contact.setFio(value);
            notifyObserver(TypeEvent.showContactData,contact,null);
        }

    }

    public void addGroupToContact(int numberContact, int numberGroup) {
        Contact contact = getContactByNumber(numberContact);
        Group group = getGroupByNumber(numberGroup);
        if (!(contact ==null)&!(group ==null)){
            contact.getGroupList().add(group);
            notifyObserver(TypeEvent.showContactData,contact,null);
        }
    }

    public void deleteGroupToContact(int numberContact, int numberGroup) {
        Contact contact = getContactByNumber(numberContact);
        Group group = getGroupByNumber(numberGroup);
        if (!(contact == null)&!(group == null)){
            contact.getGroupList().remove(group);
            notifyObserver(TypeEvent.showContactData,contact,null);
        }
    }

    public Group getGroupByNumber(int numberGroup){
        Group group = null;
        if (numberWithinBorders(numberGroup, groupList.size())) {
            group = groupList.get(numberGroup);
        }
        return group;
    }

    //Изменение состава GroupList добавление группы


    //Изменение состава GroupList удаление группы


    public boolean numberWithinBorders(int number, int max) {
        if (number >= 0 && number < max) {
            return true;
        } else {
            notifyObserver(TypeEvent.errorNumber,null,null);
            return false;
        }

    }
    /////////////////

}
