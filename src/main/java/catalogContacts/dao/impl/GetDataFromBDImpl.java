package catalogContacts.dao.impl;

import catalogContacts.dao.GetDataFromBD;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.TypeContact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GetDataFromBDImpl implements GetDataFromBD {
    private PreparedStatement preparedStatement = null;
    private int userID;
    private Connection connection = null;


    public List<Contact> getAllContact() throws DaoException {
        List<Contact> contactList= new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM getlistcontact(?)");
            preparedStatement.setInt(new Integer(1),userID);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Contact contact = addContact(result);
                contact.setContactDetailsList(getContactDetailsList(contact.getNumber()));
                contactList.add(contact);
            }
            return contactList;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении данных "+e.getMessage());
        }

    }

    public List<ContactDetails> getContactDetailsList(int contactID) throws DaoException {
        List<ContactDetails> contactDetailsList= new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM getcontactdetails(?)");
            preparedStatement.setInt(new Integer(1),contactID);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                contactDetailsList.add(addcontactDetails(result));
            }
            return contactDetailsList;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении данных "+e.getMessage());
        }

    }

    private Contact addContact(ResultSet result) throws DaoException {
        Contact contact = null;
        try {
            contact = new Contact(result.getString("contact_name"),result.getInt("contact_id"));
            return contact;
        } catch (SQLException e) {
            throw new DaoException("Ошибка считывания данных контактов "+e.getMessage());
        }


    }


    private ContactDetails addcontactDetails(ResultSet result) throws DaoException {
        ContactDetails contactDetails = null;
        try {
            try {
                TypeContact typeContact = TypeContact.valueOf(result.getString("details_type"));
                contactDetails = new ContactDetails(typeContact,result.getString("details_value"));
                return contactDetails;
            }catch (IllegalArgumentException e){
                throw new DaoException("Не верный тип контакта");
            }

        } catch (SQLException e) {
            throw new DaoException("Ошибка считывания данных контактов "+e.getMessage());
        }


    }

    public void setUserIDFromDB(String userLigin,String userPassword) throws DaoException {

        try {
            //preparedStatement = connection.prepareStatement("SELECT * FROM getUsers()");
            preparedStatement = connection.prepareStatement("SELECT * FROM getUserID(?,?)");
            preparedStatement.setString(new Integer(1),userLigin);
            preparedStatement.setString(new Integer(2),userPassword);

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                this.userID = result.getInt("getuserid");
             }


        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении данных "+e.getMessage());
        }
    }


    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Integer getUserID() {
        return userID;
    }
}
