package catalogContacts.dao.implDAO;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.GetDataFromBD;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public abstract class DaoParsing {
    private PreparedStatement preparedStatement = null;
    private Connection connection = null;
    ResultSet getResult(){
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM getlistcontact(?)");
            preparedStatement.setInt(new Integer(1),1);
            ResultSet result = preparedStatement.executeQuery();
            return result;

        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении данных "+e.getMessage());
        }
    }

}
