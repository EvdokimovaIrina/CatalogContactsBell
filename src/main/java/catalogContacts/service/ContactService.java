package catalogContacts.service;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.model.TypeContact;

import java.util.List;
import java.util.Map;

/**
 * Created by iren on 16.07.2017.
 */
public interface ContactService {

    Contact addContact(String name) throws DaoException;
    ContactDetails addContactDetails(int numberContact,Map<TypeContact,String> mapDetails) throws DaoException;
    void saveContact(Contact contact) throws DaoException;
    void deleteContact(int numberContact) throws DaoException;
    void deleteContactDetails(int numberContact,int numberContactDetails) throws DaoException;
    ContactDetails ChangeSelectedContactDetails(int numberContact,int numberContactDetails,String value) throws DaoException;
    List<Contact> showContactList(Integer numberGroup) throws DaoException;
    List<ContactDetails> showContactDetails(Contact contact) throws DaoException;
    Contact getContactByNumber(int numberContact) throws DaoException;
    Contact changeContact(int numberContact,String value) throws DaoException;
    Contact addGroupToContact(int numberContact, int numberGroup) throws DaoException;
    void deleteGroupToContact(int numberContact, int numberGroup) throws DaoException;
    void setCrudDAOContact(CrudDAO<Contact> crudDAO);
    void setCrudDAOGroup(CrudDAO<Group> crudDAOGroup);
    List<Contact> findByName(String name) throws DaoException;

}
