package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Contact;
import catalogContacts.model.Group;
import catalogContacts.model.User;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.*;

/**
 *
 */

public class DaoUser extends DaoGeneral implements CrudDAOUser<User> {

    private SessionFactory sessionFactory;

    private static Logger logger = Logger.getLogger(DaoUser.class.getName());

    public DaoUser() throws DaoException {
        super();

    }

    public void create(User user) throws DaoException {
        saveObgectToBD(user);
    }

    public void update(User object) throws DaoException {

    }

    public void delete(int number) throws DaoException {

    }

    public User getObject(int id) throws DaoException {
        return getObjectFromBDById(User.class, id);

    }

    public User authorizationUser(String login, String password) throws DaoException {
        User user = null;
            try {
            Session session = sessionFactory.getCurrentSession();
            Criteria userCriteria = session.createCriteria(User.class);
            userCriteria.add(Restrictions.eq("login", login));
            userCriteria.add(Restrictions.eq("password", password));
            user = (User) userCriteria.uniqueResult();
        } catch (Exception e) {
            logger.error("Ошибка при получении данных ", e);
            throw new DaoException("Ошибка при получении данных ", e);
        }

        return user;
        // return modelMapperUser.getObject(executionQuery(selectAuthorizationUser,login,password));
    }

    public List<User> userList() {
        return null;
    }


    public List<User> getAll() throws DaoException {
        return null;
    }


    public List<User> findByName(String name) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("from User  where login = :paramName");
            query.setParameter("paramName", name);
            List<User> userList = (List<User>) query.list();
            return userList;
        } catch (Exception e) {
            logger.error("Ошибка получения данных пользователя", e);
            throw new DaoException("Ошибка получения данных пользователя ", e);
        }
    }

    public User getUserByName(String name) throws DaoException {
        User user;
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("from User  where login = :paramName");
            query.setParameter("paramName", name);
            List<User> userList = (List<User>) query.list();
            if (userList.size() > 0) {
                user =  userList.get(0);
                user.getGroupsByUserId().size();
                user.getContactsByUserId().size();
            } else {
                user= null;
            }
            return user;
        } catch (Exception e) {
            logger.error("Ошибка получения данных пользователя", e);
            throw new DaoException("Ошибка получения данных пользователя ", e);
        }

    }

    public int countingUsers() throws DaoException {
        try {
            int counting;
            Session session = sessionFactory.getCurrentSession();
            List<User> userList = (List<User>) session.createQuery("from User").list();
            counting = userList.size();
            return counting;
        } catch (Exception e) {
            logger.error("Ошибка при получении данных ", e);
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }

    public float averageUserContact() throws DaoException {
        return averageForUser(Contact.class);
    }

    public float averageUserGroup() throws DaoException {
        return averageForUser(Group.class);
    }

    private float averageForUser(Class clazz) throws DaoException {

        try {
            int countingUser;
            int counting = 0;
            Session session = sessionFactory.getCurrentSession();
            List<User> userList = (List<User>) session.createQuery("from User").list();
            countingUser = userList.size();
            if (countingUser == 0) {
                return 0;
            }
            for (User user : userList) {
                if (clazz == Contact.class)
                    counting += user.getContactsByUserId().size();
                else if (clazz == Group.class) {
                    counting += user.getGroupsByUserId().size();
                }
            }
            return (float) counting / countingUser;

        } catch (Exception e) {
            logger.error("Ошибка при получении данных ", e);
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }

    public List<User> inactiveUsers(int n) throws DaoException {
        List<User> userList;
        try {
            Session session = sessionFactory.getCurrentSession();
            userList = (List<User>) session.createQuery("from User").list();
            Iterator<User> iter = userList.iterator();
            while (iter.hasNext()) {
                User user = iter.next();
                if (user.getContactsByUserId().size() >= n) {
                    iter.remove();
                }
            }
            return userList;
        } catch (Exception e) {
            logger.error("Ошибка при получении данных ", e);
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }

    public List<Map<User, Integer>> CountingUserContact() throws DaoException {
        return getCountingForUser(Contact.class);
    }

    public List<Map<User, Integer>> CountingUserGroup() throws DaoException {
        return getCountingForUser(Group.class);
    }

    private List<Map<User, Integer>> getCountingForUser(Class clazz) throws DaoException {
        List<Map<User, Integer>> userListMap = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            List<User> userList = (List<User>) session.createQuery("from User").list();
            for (User user : userList) {
                Map<User, Integer> mapForList = new HashMap<>();
                if (clazz == Contact.class) {
                    mapForList.put(user, user.getContactsByUserId().size());
                } else if (clazz == Group.class) {
                    mapForList.put(user, user.getGroupsByUserId().size());
                }
                userListMap.add(mapForList);
            }
            return userListMap;
        } catch (Exception e) {
            logger.error("Ошибка при получении данных ", e);
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        super.setSessionFactory(sessionFactory);
    }
}
