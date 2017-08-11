package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.dao.mappers.impl.ModelMapperContact;
import catalogContacts.model.Contact;
import catalogContacts.model.User;

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
    private ModelMapper<Contact> modelMapperContact;
    private User user;

    ResultSet getResult(String select,List param) throws DaoException {
        try {
            preparedStatement = connection.prepareStatement(select);
            for (int i = 0; i < param.size(); i++) {

                preparedStatement.setString(i, (String) param.get(i));
            }

            ResultSet result = preparedStatement.executeQuery();
            return result;

        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении данных "+e.getMessage());
        }
    }

    public DaoParsing() throws SQLException {
        modelMapperContact = new ModelMapperContact();
        connection = DBConnectionPool.getInstance().getConnectionPool().getConnection();
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ModelMapper<Contact> getModelMapperContact() {
        return modelMapperContact;
    }

    public void setModelMapperContact(ModelMapper<Contact> modelMapperContact) {
        this.modelMapperContact = modelMapperContact;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
