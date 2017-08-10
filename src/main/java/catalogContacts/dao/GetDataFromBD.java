package catalogContacts.dao;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;

import java.sql.Connection;
import java.util.List;

/**
 *
 */
public interface GetDataFromBD {
    List<Contact> getAllContact() throws DaoException;
    void setUserID(int userID);
    void setConnection(Connection connection);
    Integer getUserID();
    void setUserIDFromDB(String userLigin,String userPassword) throws DaoException;
}
