package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Contact;

import java.util.List;

/**
 *
 */
public class DaoContactJackson extends DaoContact {

    @Override
    public Contact getObject(int id) throws DaoXmlException {
        return super.getObject(id);
    }

    @Override
    public List<Contact> getAll() throws DaoXmlException {
        return super.getAll();
    }

    @Override
    public List<Contact> findByName(String name) throws DaoXmlException {
        return super.findByName(name);
    }

    @Override
    public int toFormANewId() throws DaoXmlException {
        return super.toFormANewId();
    }

    @Override
    List<Contact> xmlToListObject() throws DaoXmlException {
        return super.xmlToListObject();
    }
}
