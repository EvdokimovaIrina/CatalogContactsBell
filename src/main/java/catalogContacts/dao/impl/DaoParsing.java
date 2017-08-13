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

    protected ResultSet executionQuery(String select,Object... params) throws DaoException {
        try {
            preparedStatement = connection.prepareStatement(select);
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof Integer){
                    preparedStatement.setInt(i+1, (Integer) params[i]);
                }else if (params[i] instanceof String){
                preparedStatement.setString(i+1, (String) params[i]);
                }
            }
            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении данных ",e);
        }
    }

    public DaoParsing() throws DaoException {
        try {
            connection = DBConnectionPool.getInstance().getConnectionPool().getConnection();
        } catch (SQLException e) {
           throw new DaoException("Ошибка подключения к БД ",e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}
