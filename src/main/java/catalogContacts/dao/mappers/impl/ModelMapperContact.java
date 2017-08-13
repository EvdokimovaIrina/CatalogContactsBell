package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ModelMapperContact implements ModelMapper<Contact>{
    public Contact creatObject(ResultSet result) throws DaoException {
        try {
            while (result.next()) {

                Contact contact = new Contact(result.getString("contact_name"),result.getInt("contact_id"));
                return contact;
            }
        } catch (SQLException e) {
        throw new DaoException("Ошибка получения контакта "+e.getMessage(),e);
        }
        return null;
    }

    public List<Contact> creatObjectList(ResultSet result) throws DaoException {
        List<Contact> contactList=new ArrayList<>();
        try {
            while (result.next()) {
                Contact contact = new Contact(result.getString("contact_name"),result.getInt("contact_id"));
                contactList.add(contact);
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка получения контактов "+e.getMessage(),e);
        }
        return contactList;
    }
}
