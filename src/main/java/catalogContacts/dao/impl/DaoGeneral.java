package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;
import catalogContacts.model.User;
import org.apache.log4j.Logger;
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
public abstract class DaoGeneral {
    private static Logger logger = Logger.getLogger(DaoGeneral.class.getName());

    public void saveObgectToBD(Object object) throws DaoException {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(object);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Ошибка при записи в БД ", e);
            throw new DaoException("Ошибка при записи в БД ", e);
        }

    }

    public void deleteObgectFromBD(Class clazz, int id) throws DaoException {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.delete(clazz.cast(session.get(clazz, id)));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Ошибка при записи в БД ", e);
            throw new DaoException("Ошибка при удалении из БД ", e);
        }

    }

    public <T> T getObjectFromBDById(Class<T> clazz, int id) throws DaoException {
        Transaction transaction = null;
        T object = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            object = clazz.cast(session.get(clazz, id));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Ошибка при получении данных ", e);
            throw new DaoException("Ошибка при получении данных ", e);
        }

        return object;

    }

}
