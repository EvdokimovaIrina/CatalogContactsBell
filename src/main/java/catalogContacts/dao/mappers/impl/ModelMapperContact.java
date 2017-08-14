package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ModelMapperContact implements ModelMapper<Contact> {
    public Contact getObject(List<Map<String, String>> listMapResulSet) throws DaoException {
        for (Map<String, String> mapOfList : listMapResulSet) {
            return mapToObject(mapOfList);
        }
        return null;
    }

    public List<Contact> getListOfObjects(List<Map<String, String>> listMapResulSet) throws DaoException {
        List<Contact> contactList = new ArrayList<>();

        for (Map<String, String> mapOfList : listMapResulSet) {
            contactList.add(mapToObject(mapOfList));
        }
        return contactList;
    }

    public Contact mapToObject(Map<String, String> mapOfList) throws DaoException {
        try {
            String name = mapOfList.get("contact_name");
            int id = Integer.parseInt(mapOfList.get("contact_id"));
            Contact contact = new Contact(name, id);
            return contact;
        } catch (NumberFormatException | NullPointerException e) {
            throw new DaoException("Ошибка получения контакта " + e.getMessage(), e);
        }
    }
}
