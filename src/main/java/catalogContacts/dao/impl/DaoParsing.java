package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;
import catalogContacts.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public void saveObgectToBD(Object object) throws DaoException {
        //запишем новый контакт
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(object);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }

    public <T> T getObjectFromBDById(Class<T> clazz, int id) throws DaoException {
        Transaction transaction = null;
        T object=null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            object = clazz.cast(session.get(clazz,id));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DaoException("Ошибка при получении данных ", e);
        }

        return object;

    }

  /*  public <T> T removeObjectFromBDById(Class<T> clazz, int id) throws DaoException {
        Transaction transaction = null;
        T object=new T();
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            object.setTaskId(taskId);
            session.delete(task);

            object = clazz.cast(session.get(clazz,id));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DaoException("Ошибка при получении данных ",e);
        } finally {
            HibernateUtil.closeSession();
        }

        return object;

    }*/

}
