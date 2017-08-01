package catalogContacts.service.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.dao.factory.AbstractFactoryDao;
import catalogContacts.event.Event;
import catalogContacts.event.Observer;
import catalogContacts.event.TypeEvent;
import catalogContacts.model.*;
import catalogContacts.service.ContactService;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by iren on 20.07.2017.
 */
public final class ContactServiceImpl implements ContactService, Observer.Observable {
    private static ContactServiceImpl instance;
    private CrudDAO<Contact> crudDAOContact;
    private CrudDAO<Group> crudDAOGroup;
    private List<Observer> ObserversList = new ArrayList<>();

    // Singleton
    private ContactServiceImpl() {

    }

    public static synchronized ContactServiceImpl getInstance() {
        if (instance == null) {
            instance = new ContactServiceImpl();
        }
        return instance;
    }
    //////


    public void setCrudDAOContact(CrudDAO<Contact> crudDAOContact) {
        this.crudDAOContact = crudDAOContact;
    }

    public void setCrudDAOGroup(CrudDAO<Group> crudDAOGroup) {
        this.crudDAOGroup = crudDAOGroup;
    }

    public void findByName(String name) {
        try {
            List<Contact> contactList = crudDAOContact.findByName(name);
            notifyObserver(TypeEvent.showContactList, contactList, null);
        } catch (DaoXmlException e) {
            e.printStackTrace();
        }
    }

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
            observer.handleEvent(new Event(typeEvent, mainObject, value));
        }
    }

    private void notifyObserverWithAneError(DaoXmlException e) {
        notifyObserver(TypeEvent.ERROR, e.getMessage(), null);
    }
    ////////

    //
    //добавление контакта
    public void addContact(String name) {
        Contact contact = new Contact(name);
        contact.setContactDetailsList(new ArrayList<>());
        contact.setGroupList(new ArrayList<>());
        saveContact(contact);
    }

    public void addContactDetails(int numberContact, Map<TypeContact, String> mapDetails) {

        Contact contact = null;

        try {
            contact = crudDAOContact.getObject(numberContact);

            for (Map.Entry entry : mapDetails.entrySet()) {
                ContactDetails contactDetails = new ContactDetails((TypeContact) entry.getKey(), (String) entry.getValue());
                contact.getContactDetailsList().add(contactDetails);
            }
            crudDAOContact.update(contact);
            notifyObserver(TypeEvent.showContactDetails, contact, mapDetails);

        } catch (DaoXmlException e) {
            notifyObserverWithAneError(e);
        }
    }

    //Сохранение контакта в хранилище
    public void saveContact(Contact contact) {
        try {
            contact.setNumber(crudDAOContact.toFormANewId());
            crudDAOContact.create(contact);
            List<Contact> contactList = crudDAOContact.getAll();
            notifyObserver(TypeEvent.showContactList, contactList, null);

        } catch (DaoXmlException e) {
            notifyObserverWithAneError(e);
        }
    }

    //удаление контакта из списка
    public void deleteContact(int numberContact) {
        try {
            crudDAOContact.delete(numberContact);

            notifyObserver(TypeEvent.showContactList, crudDAOContact.getAll(), null);
        } catch (DaoXmlException e) {
            notifyObserverWithAneError(e);
        }

    }

    public void deleteContactDetails(int numberContact, int numberContactDetails) {

        try {
            Contact contact = getContactByNumber(numberContact);

            List<ContactDetails> contactDetailsList = contact.getContactDetailsList();
            if (numberContactDetails >= 0 & numberContactDetails < contactDetailsList.size()) {
                contactDetailsList.remove(numberContactDetails);
            } else {
                notifyObserver(TypeEvent.errorNumber, null, null);
            }
            crudDAOContact.update(contact);
        } catch (DaoXmlException e) {
            notifyObserverWithAneError(e);
        }

    }

    public void ChangeSelectedContactDetails(int numberContact, int numberContactDetails, String value) {
        try {
            Contact contact = getContactByNumber(numberContact);

            List<ContactDetails> contactDetailsList = contact.getContactDetailsList();
            if (numberContactDetails >= 0 & numberContactDetails < contactDetailsList.size()) {
                contactDetailsList.get(numberContactDetails).setValue(value);
            } else {
                notifyObserver(TypeEvent.errorNumber, null, null);
            }
            crudDAOContact.update(contact);
        } catch (DaoXmlException e) {
            notifyObserverWithAneError(e);
        }
    }

    public void showContactList(Integer numberGroup) {
        if (numberGroup == null) {
            try {
                notifyObserver(TypeEvent.showContactList, crudDAOContact.getAll(), null);
            } catch (DaoXmlException e) {
                notifyObserverWithAneError(e);
            }
        } else {
            Group group = GroupServiceImpl.getInstance().findByNumber(numberGroup);
            notifyObserver(TypeEvent.showContactListFromGroup, contactListFromGroup(group), group);
        }

    }

    public List<Contact> contactListFromGroup(Group group) {
        List<Contact> list = new ArrayList<>();
        try {
            for (Contact contact : crudDAOContact.getAll()) {
                if (contact.getGroupList().contains(group)) {
                    list.add(contact);
                }
            }
        } catch (DaoXmlException ignored) {

        }
        return list;
    }


    public Contact getContactByNumber(int numberContact) throws DaoXmlException {
        Contact contact = crudDAOContact.getObject(numberContact);

        return contact;
    }

    public void showContactDetails(int numberContact) {

        try {
            Contact contact = getContactByNumber(numberContact);

            notifyObserver(TypeEvent.showContactData, contact, null);

        } catch (DaoXmlException e) {
            notifyObserverWithAneError(e);
        }
    }

    public void changeContact(int numberContact, String value) {

        try {
            Contact contact = getContactByNumber(numberContact);
            if (!(contact == null)) {
                contact.setFio(value);
                crudDAOContact.update(contact);
                notifyObserver(TypeEvent.showContactData, contact, null);
            }

        } catch (DaoXmlException e) {
            notifyObserverWithAneError(e);
        }


    }

    public void addGroupToContact(int numberContact, int numberGroup) {

        try {
            Contact contact = getContactByNumber(numberContact);
            Group group = getGroupByNumber(numberGroup);
            if (!(contact == null) & !(group == null)) {
                contact.getGroupList().add(group);
                crudDAOContact.update(contact);
                notifyObserver(TypeEvent.showContactData, contact, null);
            }
        } catch (DaoXmlException e) {
            notifyObserverWithAneError(e);
        }

    }

    public void deleteGroupToContact(int numberContact, int numberGroup) {

        try {
            Contact contact = getContactByNumber(numberContact);
            Group group = getGroupByNumber(numberGroup);
            if (!(contact == null) & !(group == null)) {
                contact.getGroupList().remove(group);
                crudDAOContact.update(contact);
                notifyObserver(TypeEvent.showContactData, contact, null);
            }

        } catch (DaoXmlException e) {
            e.printStackTrace();
        }

    }

    public Group getGroupByNumber(int numberGroup) {
        Group group = null;
        try {
            group = crudDAOGroup.getObject(numberGroup);
        } catch (DaoXmlException e) {
            notifyObserverWithAneError(e);
        }

        return group;
    }


}
