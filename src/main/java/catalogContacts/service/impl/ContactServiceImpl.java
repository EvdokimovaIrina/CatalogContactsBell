package catalogContacts.service.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.impl.DaoContact;
import catalogContacts.dao.impl.DaoGroup;
import catalogContacts.model.*;
import catalogContacts.service.ContactService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by iren on 20.07.2017.
 */
public final class ContactServiceImpl implements ContactService {
    // private static ContactServiceImpl instance;
    private CrudDAO<Contact> crudDAOContact;
    private CrudDAO<Group> crudDAOGroup;

    // Singleton

    private ContactServiceImpl() {
        try {
            crudDAOContact = new DaoContact();
            crudDAOGroup = new DaoGroup();
        } catch (DaoException e) {
            crudDAOContact = null;
            crudDAOGroup = null;
        }
    }

    public static ContactServiceImpl getInstance() {
        return ContactServiceImplHolder.instance;
    }

    private static class ContactServiceImplHolder {
        private static final ContactServiceImpl instance = new ContactServiceImpl();
    }

    //////

    public synchronized List<Contact> findByName(String name) throws DaoException {

        return crudDAOContact.findByName(name);

    }

    //
    //добавление контакта
    public void addContact(String name) throws DaoException {
        Contact contact = new Contact(name);
        saveContact(contact);
    }

    public void addContactDetails(int numberContact, Map<TypeContact, String> mapDetails) throws DaoException {

        Contact contact = null;
        synchronized (this) {

            contact = crudDAOContact.getObject(numberContact);

            for (Map.Entry entry : mapDetails.entrySet()) {
                ContactDetails contactDetails = new ContactDetails((TypeContact) entry.getKey(), (String) entry.getValue());
                contact.getContactDetailsList().add(contactDetails);
            }
            crudDAOContact.update(contact);
        }

    }

    //Сохранение контакта в хранилище
    public void saveContact(Contact contact) throws DaoException {
        synchronized (this) {
            crudDAOContact.create(contact);
        }
    }

    //удаление контакта из списка
    public void deleteContact(int numberContact) throws DaoException {
        synchronized (this) {
            crudDAOContact.delete(numberContact);
        }

    }

    public void deleteContactDetails(int numberContact, int numberContactDetails) throws DaoException {
        Contact contact = getContactByNumber(numberContact);

        List<ContactDetails> contactDetailsList = contact.getContactDetailsList();

        Iterator<ContactDetails> iter = contactDetailsList.iterator();
        while (iter.hasNext()) {
            ContactDetails contactDetails = iter.next();
            if (contactDetails.getId() == numberContactDetails) {
                iter.remove();
                break;
            }
        }

        synchronized (this) {
            crudDAOContact.update(contact);
        }


    }

    public void ChangeSelectedContactDetails(int numberContact, int numberContactDetails, String value) throws DaoException {

        Contact contact = getContactByNumber(numberContact);

        List<ContactDetails> contactDetailsList = contact.getContactDetailsList();

        Iterator<ContactDetails> iter = contactDetailsList.iterator();
        while (iter.hasNext()) {
            ContactDetails contactDetails = iter.next();
            if (contactDetails.getId() == numberContactDetails) {
                contactDetails.setValue(value);
                break;
            }
        }
        synchronized (this) {
            crudDAOContact.update(contact);
        }
    }

    public List<Contact> showContactList(Integer numberGroup) throws DaoException {
        if (numberGroup == null) {
            return crudDAOContact.getAll();
        } else {
            Group group = GroupServiceImpl.getInstance().findByNumber(numberGroup);
            return contactListFromGroup(group);
        }
    }

    public List<Contact> contactListFromGroup(Group group) {
        List<Contact> list = new ArrayList<>();
        try {
            synchronized (this) {
                for (Contact contact : crudDAOContact.getAll()) {
                    if (contact.getGroupList().contains(group)) {
                        list.add(contact);
                    }
                }
            }
        } catch (DaoException ignored) {

        }
        return list;
    }


    public Contact getContactByNumber(int numberContact) throws DaoException {
        Contact contact;
        synchronized (this) {
            contact = crudDAOContact.getObject(numberContact);
        }
        return contact;
    }

    public List<ContactDetails> showContactDetails(int numberContact) throws DaoException {

        Contact contact = getContactByNumber(numberContact);

        return contact.getContactDetailsList();
    }

    public void changeContact(int numberContact, String value) throws DaoException {
        Contact contact = getContactByNumber(numberContact);
        if (!(contact == null)) {
            contact.setFio(value);
            synchronized (this) {
                crudDAOContact.update(contact);
            }
        }
    }

    public void addGroupToContact(int numberContact, int numberGroup) throws DaoException {

        Contact contact = getContactByNumber(numberContact);
        Group group = getGroupByNumber(numberGroup);
        if (!(contact == null) & !(group == null)) {
            contact.getGroupList().add(group);
            synchronized (this) {
                crudDAOContact.update(contact);
            }
        }
    }

    public void deleteGroupToContact(int numberContact, int numberGroup) throws DaoException {

        Contact contact = getContactByNumber(numberContact);
       if (!(contact == null)) {
            Iterator<Group> iter = contact.getGroupList().iterator();
            while (iter.hasNext()) {
                Group group1 = iter.next();
                if (group1.getNumber() == numberGroup) {
                    iter.remove();
                    break;
                }
            }
            synchronized (this) {
                crudDAOContact.update(contact);
            }
        }
    }

    public void setCrudDAOContact(CrudDAO<Contact> crudDAOContact) {
        this.crudDAOContact = crudDAOContact;
    }

    public void setCrudDAOGroup(CrudDAO<Group> crudDAOGroup) {
        this.crudDAOGroup = crudDAOGroup;
    }

    public Group getGroupByNumber(int numberGroup) throws DaoException {
        Group group = null;
        synchronized (this) {
            group = crudDAOGroup.getObject(numberGroup);
        }
        return group;
    }


}
