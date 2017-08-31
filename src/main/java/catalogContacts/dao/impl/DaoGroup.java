package catalogContacts.dao.impl;

import catalogContacts.context.SecurityContextHolder;
import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Group;
import catalogContacts.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoGroup extends DaoGeneral implements CrudDAO<Group> {
    @Autowired
    private SessionFactory sessionFactory;
    private static Logger logger = Logger.getLogger(DaoGroup.class.getName());

    public DaoGroup() throws DaoException {
        super();
    }

    public void create(Group group) throws DaoException {
        group.setUserByUserId(getObjectFromBDById(User.class, SecurityContextHolder.getLoggedUserID()));
        saveObgectToBD(group);
    }

    public void update(Group group) throws DaoException {
        saveObgectToBD(group);
    }

    public void delete(int number) throws DaoException {
        deleteObgectFromBD(Group.class, number);
    }

    public Group getObject(int id) throws DaoException {
        Transaction transaction = null;
        try {
            Group group;
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            group = (Group) session.load(Group.class, id);
            group.getContactList().size();
            transaction.commit();
            return group;
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Ошибка при получении данных ", e);
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }

    public List<Group> getAll() throws DaoException {

        int userID = SecurityContextHolder.getLoggedUserID();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            List<Group> groupList;
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, userID);
            user.getGroupsByUserId().size(); //для инициализации
            groupList = user.getGroupsByUserId();
            transaction.commit();
            return groupList;
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Ошибка получения списка группы ", e);
            throw new DaoException("Ошибка получения списка группы ", e);
        }
    }

    public List<Group> findByName(String name) throws DaoException {
        int userID = SecurityContextHolder.getLoggedUserID();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            List<Group> groupList = new ArrayList<>();
            transaction = session.beginTransaction();
            User user = (User) session.load(User.class, userID);
            user.getGroupsByUserId().size();
            for (Group group : user.getGroupsByUserId()) {
                if (group.getName().contains(name)) {
                    groupList.add(group);
                }
            }
            transaction.commit();
            return groupList;
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Ошибка получения списка группы ", e);
            throw new DaoException("Ошибка получения списка группы ", e);
        }
    }

}
