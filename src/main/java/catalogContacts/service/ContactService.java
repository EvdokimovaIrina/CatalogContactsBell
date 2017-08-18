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

    void addContact(String name);
    void addContactDetails(int numberContact,Map<TypeContact,String> mapDetails);
    void saveContact(Contact contact);
    void deleteContact(int numberContact);
    void deleteContactDetails(int numberContact,int numberContactDetails);
    void ChangeSelectedContactDetails(int numberContact,int numberContactDetails,String value);
    List<Contact> showContactList(Integer numberGroup) throws DaoException;
    List<ContactDetails> showContactDetails(int numberContact) throws DaoException;
    void changeContact(int numberContact,String value);
    void addGroupToContact(int numberContact, int numberGroup);
    void deleteGroupToContact(int numberContact, int numberGroup);
    void setCrudDAOContact(CrudDAO<Contact> crudDAO);
    void setCrudDAOGroup(CrudDAO<Group> crudDAOGroup);
    void findByName(String name);

}
