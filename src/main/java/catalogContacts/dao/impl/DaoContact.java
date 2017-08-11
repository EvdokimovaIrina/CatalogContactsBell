package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DaoContact extends DaoParsing implements CrudDAO<Contact>{
    private User user;
    private ModelMapper<Contact> modelMapperContact;

    public DaoContact() throws SQLException {
        super();
    }

    public void create(Contact object) throws DaoException {

    }

    public void update(Contact object) throws DaoException {

    }

    public void delete(int number) throws DaoException {

    }

    public Contact getObject(int id) throws DaoException {
        return null;
    }

    public List<Contact> getAll() throws DaoException {
        List<Contact> contactList = new ArrayList<>();

        try {
            List param=new ArrayList();
            param.add(user.getId());
            ResultSet result = getResult("SELECT * FROM getlistcontact(?)",param);
            while (result.next()) {
                Contact contact = modelMapperContact.creatObject(result);
                contact.setContactDetailsList(getContactDetailsList(contact));
                contactList.add(contact);
            }

            return contactList;
        } catch (SQLException e) {
            throw new DaoException("Ошибка получения данных "+e.getMessage());
        }

    }

    private List <ContactDetails> getContactDetailsList(Contact contact){
        List<ContactDetails> contactDetailses = new ArrayList<>();
        return contactDetailses;
    }

    public List<Contact> findByName(String name) throws DaoException {
        return null;
    }

    public int toFormANewId() throws DaoException {
        return 0;
    }


}
