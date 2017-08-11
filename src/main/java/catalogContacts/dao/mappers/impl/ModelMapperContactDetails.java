package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.User;

import java.sql.ResultSet;

/**
 *
 */
public class ModelMapperContactDetails implements ModelMapper<ContactDetails>{
    public ContactDetails creatObject(ResultSet result) {
        ContactDetails contactDetails = new ContactDetails();
        return contactDetails;
    }


}
