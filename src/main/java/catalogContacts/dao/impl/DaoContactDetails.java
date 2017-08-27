package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.ContactDetails;

import java.util.List;

/**
 *
 */
public class DaoContactDetails extends DaoGeneral implements CrudDAO<ContactDetails>{

    public DaoContactDetails() throws DaoException {
        super();
    }

    public void create(ContactDetails contactDetails) throws DaoException {
      saveObgectToBD(contactDetails);
    }

    public void update(ContactDetails contactDetails) throws DaoException {
        saveObgectToBD(contactDetails);
    }

    public void delete(int number) throws DaoException {
         deleteObgectFromBD(ContactDetails.class,number);
    }

    public ContactDetails getObject(int id) throws DaoException {
        return getObjectFromBDById(ContactDetails.class,id);
    }

    public List<ContactDetails> getAll() throws DaoException {
        return null;
    }

    public List<ContactDetails> findByName(String name) throws DaoException {
        return null;
    }
}
