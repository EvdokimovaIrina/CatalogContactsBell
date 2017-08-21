package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class DaoParsing {

    protected List<Map<String, String>> executionQuery(String select, Object... params) throws DaoException {
        List<Map<String, String>> listMapResulSet = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        DataSource dataSource;
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/catalogcontacs");
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(select);

            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof Integer) {
                    preparedStatement.setInt(i + 1, (Integer) params[i]);
                } else if (params[i] instanceof String) {
                    preparedStatement.setString(i + 1, (String) params[i]);
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Map<String, String> mapForList = new HashMap<>();
                for (int i = 1; i <= columns; i++) {
                    mapForList.put(resultSet.getMetaData().getColumnName(i), resultSet.getString(i));
                }
                listMapResulSet.add(mapForList);
            }
            connection.close();
            return listMapResulSet;

        } catch (NamingException | SQLException e) {
            throw new DaoException("Ошибка при получении данных ", e);
        }
        finally{
            closeConnection(connection);

        }
    }

    private void closeConnection(Connection connection) throws DaoException {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }


    public DaoParsing() throws DaoException {

    }


}
