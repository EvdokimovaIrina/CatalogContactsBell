package catalogContacts.service.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.impl.DaoContact;
import catalogContacts.dao.impl.DaoContactDetails;
import catalogContacts.dao.impl.DaoGroup;
import catalogContacts.model.*;
import catalogContacts.service.ContactService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by iren on 20.07.2017.
 */
public final class ContactServiceImpl implements ContactService {
    private CrudDAO<Contact> crudDAOContact;
    private CrudDAO<Group> crudDAOGroup;
    private CrudDAO<ContactDetails> crudDAOContactDetails;
    private static Logger logger = Logger.getLogger(ContactServiceImpl.class.getName());
    // Singleton

    private ContactServiceImpl() {
        try {
            synchronized (this) {
                crudDAOContact = new DaoContact();
                crudDAOGroup = new DaoGroup();
                crudDAOContactDetails = new DaoContactDetails();
            }

        } catch (DaoException e) {
            crudDAOContact = null;
            crudDAOGroup = null;
            crudDAOContactDetails = null;
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
                contactDetails.setContactByContactId(contact);
                crudDAOContactDetails.create(contactDetails);
            }
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
        synchronized (this) {
            crudDAOContactDetails.delete(numberContactDetails);
        }
    }

    public void ChangeSelectedContactDetails(int numberContact, int numberContactDetails, String value) throws DaoException {
        synchronized (this) {
            Contact contact = getContactByNumber(numberContact);
            List<ContactDetails> contactDetailsList = contact.getContactDetailsList();
            Iterator<ContactDetails> iter = contactDetailsList.iterator();
            logger.info("Изменение контактной информации ");
            logger.debug("Данные контакта : id = " + numberContact + ", name = " + contact.getFio());

            while (iter.hasNext()) {
                ContactDetails contactDetails = iter.next();
                if (contactDetails.getId() == numberContactDetails) {
                    logger.debug("Данные контактной информации : id = " + numberContactDetails +
                            ", type = " + contactDetails.getType() + ", value = " + contactDetails.getValue());
                    logger.debug("Новые данные контактной информации : id = " + numberContactDetails +
                            ", type = " + contactDetails.getType() + ", value = " + value);
                    contactDetails.setValue(value);
                    break;
                }
            }
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

    private List<Contact> contactListFromGroup(Group group) {
        List<Contact> list;
        list = group.getContactList();

        return list;
    }


    public Contact getContactByNumber(int numberContact) throws DaoException {
        Contact contact;
        synchronized (this) {
            contact = crudDAOContact.getObject(numberContact);
        }
        return contact;
    }

    public List<ContactDetails> showContactDetails(Contact contact) throws DaoException {

        return contact.getContactDetailsList();
    }

    public void changeContact(int numberContact, String value) throws DaoException {
        Contact contact = getContactByNumber(numberContact);

        if (!(contact == null)) {
            contact.setFio(value);
            synchronized (this) {
                logger.info("Изменение наименования контакта ");
                logger.debug("Данные контакта : id = " + numberContact + ", name = " + contact.getFio());
                crudDAOContact.update(contact);
                logger.debug("новые данные контакта : id = " + numberContact + ", name = " + value);
            }
        }
    }

    public void addGroupToContact(int numberContact, int numberGroup) throws DaoException {

        Contact contact = getContactByNumber(numberContact);
        Group group = getGroupByNumber(numberGroup);
        contact.getGroupList().add(group);
        if (!(contact == null) & !(group == null)) {
            synchronized (this) {
                logger.info("Добавление группы к контакту ");
                logger.debug("Данные контакта : id = " + numberContact + ", name = " + contact.getFio());
                logger.debug("Группа: id = " + numberGroup + ", name = " + group.getName());
                crudDAOContact.update(contact);
            }
        }
    }

    public void deleteGroupToContact(int numberContact, int numberGroup) throws DaoException {
        synchronized (this) {
            Contact contact = getContactByNumber(numberContact);
            logger.info("Удаление группы из контакта");
            if (contact == null) {
                logger.error("Контакт с id = " + numberContact + " не найден");
            } else {
                logger.debug("Данные контакта : id = " + numberContact + ", name = " + contact.getFio());

                Iterator<Group> iter = contact.getGroupList().iterator();
                while (iter.hasNext()) {
                    Group group1 = iter.next();
                    if (group1.getNumber() == numberGroup) {
                        logger.debug("Группа: id = " + group1.getNumber() + ", name = " + group1.getName());
                        iter.remove();
                        break;
                    }
                }
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
        Group group;
        synchronized (this) {
            group = crudDAOGroup.getObject(numberGroup);
        }
        return group;
    }


}
