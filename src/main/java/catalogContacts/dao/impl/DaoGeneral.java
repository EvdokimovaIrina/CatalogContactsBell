package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public abstract class DaoGeneral {

    private SessionFactory sessionFactory;

    private static Logger logger = Logger.getLogger(DaoGeneral.class.getName());

//    @Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)

    public void saveObgectToBD(Object object) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(object);
        } catch (Exception e) {
            logger.error("Ошибка при записи в БД ", e);
            throw new DaoException("Ошибка при записи в БД ", e);
        }

    }

    public void deleteObgectFromBD(Class clazz, int id) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(clazz.cast(session.get(clazz, id)));
        } catch (Exception e) {
            logger.error("Ошибка при записи в БД ", e);
            throw new DaoException("Ошибка при удалении из БД ", e);
        }

    }

    public <T> T getObjectFromBDById(Class<T> clazz, int id) throws DaoException {
        T object = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            object = clazz.cast(session.load(clazz, id));
        } catch (Exception e) {
            logger.error("Ошибка при получении данных ", e);
            throw new DaoException("Ошибка при получении данных ", e);
        }

        return object;

    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
