package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.TypeContact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ModelMapperContactDetails implements ModelMapper <ContactDetails>{
    public ContactDetails creatObject(ResultSet result) throws DaoException {
         try {
            while (result.next()) {
                TypeContact typeContact = TypeContact.valueOf(result.getString("details_type"));
                ContactDetails contactDetails = new ContactDetails(result.getInt("details_id"),typeContact,result.getString("details_value"));
                return  contactDetails;

            }
        } catch (SQLException|IllegalArgumentException e) {
                throw new DaoException("Ошибка получения контактной информации");
        }
        return null;
    }

    public List<ContactDetails> creatObjectList(ResultSet result) throws DaoException {
        List<ContactDetails> contactDetailsList = new ArrayList<>();
        try {
            while (result.next()) {
                TypeContact typeContact = TypeContact.valueOf(result.getString("details_type"));
                ContactDetails contactDetails = new ContactDetails(result.getInt("details_id"),typeContact,result.getString("details_value"));
                contactDetailsList.add(contactDetails);

            }
        } catch (SQLException|IllegalArgumentException e) {
            throw new DaoException("Ошибка получения контактной информации");
        }
        return contactDetailsList;
    }


}
