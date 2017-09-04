package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Group;
import catalogContacts.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;


public class DaoGroup extends DaoGeneral implements CrudDAO<Group> {

    private SessionFactory sessionFactory;
    private static Logger logger = Logger.getLogger(DaoGroup.class.getName());
    private CrudDAOUser<User> userCrudDAO;

    public DaoGroup() throws DaoException {
        super();
    }

    public void create(Group group) throws DaoException {
        group.setUserByUserId(userCrudDAO.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName()));
        saveObgectToBD(group);
    }

    public void update(Group group) throws DaoException {
        saveObgectToBD(group);
    }

    public void delete(int number) throws DaoException {
        deleteObgectFromBD(Group.class, number);
    }

    public Group getObject(int id) throws DaoException {
        try {
            Group group;
            Session session = sessionFactory.getCurrentSession();
            group = (Group) session.load(Group.class, id);
            group.getContactList().size();
            return group;
        } catch (Exception e) {
            logger.error("Ошибка при получении данных ", e);
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }

    public List<Group> getAll() throws DaoException {
        try {
            List<Group> groupList;
            User user = userCrudDAO.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
            if (user==null){
                logger.error("Ошибка авторизации, пользователь не найден");
                throw new DaoException("Ошибка авторизации, пользователь не найден");
            }
            user.getGroupsByUserId().size(); //для инициализации
            groupList = user.getGroupsByUserId();
            return groupList;
        } catch (Exception e) {
            logger.error("Ошибка получения списка группы ", e);
            throw new DaoException("Ошибка получения списка группы ", e);
        }
    }

    public List<Group> findByName(String name) throws DaoException {
        try {
            List<Group> groupList = new ArrayList<>();
            User user = userCrudDAO.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
            if (user==null){
                logger.error("Ошибка авторизации, пользователь не найден");
                throw new DaoException("Ошибка авторизации, пользователь не найден");
            }
            user.getGroupsByUserId().size();
            for (Group group : user.getGroupsByUserId()) {
                if (group.getName().contains(name)) {
                    groupList.add(group);
                }
            }
            return groupList;
        } catch (Exception e) {
            logger.error("Ошибка получения списка группы ", e);
            throw new DaoException("Ошибка получения списка группы ", e);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        super.setSessionFactory(sessionFactory);
    }

    public CrudDAOUser<User> getUserCrudDAO() {
        return userCrudDAO;
    }

    public void setUserCrudDAO(CrudDAOUser<User> userCrudDAO) {
        this.userCrudDAO = userCrudDAO;
    }
}
