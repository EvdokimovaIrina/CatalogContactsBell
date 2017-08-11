package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.model.Contact;

import java.sql.ResultSet;

/**
 *
 */
public class ModelMapperContact implements ModelMapper<Contact>{
    public Contact creatObject(ResultSet result) {
        Contact contact=null;
        return contact;
    }


}
