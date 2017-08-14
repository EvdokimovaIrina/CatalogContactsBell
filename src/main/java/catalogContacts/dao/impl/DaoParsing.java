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
import java.util.Map;

/**
 *
 */
public abstract class DaoParsing {

    protected ResultSet executionQuery(String select, Object... params) throws DaoException {
        try (Connection connection = DBConnectionPool.getInstance().getConnectionPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(select)
        ) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof Integer) {
                    preparedStatement.setInt(i + 1, (Integer) params[i]);
                } else if (params[i] instanceof String) {
                    preparedStatement.setString(i + 1, (String) params[i]);
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;

        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }

    private ModelMapper factoryMapper(ResultSet resultSet,){



    }

    public DaoParsing() throws DaoException {

    }


}
