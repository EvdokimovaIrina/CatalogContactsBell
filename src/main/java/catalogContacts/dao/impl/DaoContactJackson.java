package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Contact;

import java.util.List;

/**
 *
 */
public class DaoContactJackson implements CrudDAO<Contact> {

    public void create(Contact object) {

    }

    public void update(Contact object) {

    }

    public void delete(int number) {

    }

    public Contact getObject(int id) {
        return null;
    }

    public List<Contact> getAll() {
        return null;
    }

    public Contact findTheName(String name) {
        return null;
    }

    public int toFormANewId() throws DaoXmlException {
        return 0;
    }

    public List<Contact> xmlToListObject() {
        return null;
    }

    public Contact getContact() {
        return null;
    }
}
