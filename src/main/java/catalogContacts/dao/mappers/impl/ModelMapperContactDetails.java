package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.TypeContact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ModelMapperContactDetails implements ModelMapper<ContactDetails> {

    public ContactDetails creatObject(List<Map<String, String>> listMapResulSet) throws DaoException {
        for (Map<String, String> mapOfList : listMapResulSet) {
            return mapToObject(mapOfList);
        }

        return null;
    }

    public List<ContactDetails> creatObjectList(List<Map<String, String>> listMapResulSet) throws DaoException {
        List<ContactDetails> contactDetailsList = new ArrayList<>();
        for (Map<String, String> mapOfList : listMapResulSet) {
            contactDetailsList.add(mapToObject(mapOfList));
        }
        return contactDetailsList;
    }

    public ContactDetails mapToObject(Map<String, String> mapOfList) throws DaoException {
        try {
            TypeContact typeContact = TypeContact.valueOf(mapOfList.get("details_type"));
            String value = mapOfList.get("details_value");
            int id = Integer.parseInt(mapOfList.get("details_id"));
            return new ContactDetails(id, typeContact, value);
        } catch (NumberFormatException | NullPointerException e) {
            throw new DaoException("Ошибка получения контактной информации " + e.getMessage(), e);
        }
    }


}
