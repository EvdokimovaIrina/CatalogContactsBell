package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.dao.mappers.impl.ModelMapperGroup;
import catalogContacts.model.Group;
import catalogContacts.context.SecurityContextHolder;
import catalogContacts.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 *
 */
public class DaoGroup extends DaoGeneral implements CrudDAO<Group> {

    public DaoGroup() throws DaoException {
        super();
    }

    public void create(Group group) throws DaoException {
        group.setUserByUserId(getObjectFromBDById(User.class,SecurityContextHolder.getLoggedUserID()));
        saveObgectToBD(group);
    }

    public void update(Group group) throws DaoException {
        saveObgectToBD(group);
    }

    public void delete(int number) throws DaoException {
        deleteObgectFromBD(Group.class,number);
    }

    public Group getObject(int id) throws DaoException {
        return getObjectFromBDById(Group.class,id);
    }

    public List<Group> getAll() throws DaoException {

        int userID = SecurityContextHolder.getLoggedUserID();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class,userID);
            user.getGroupsByUserId().size(); //для инициализации
            List<Group>  groupList = user.getGroupsByUserId();
            transaction.commit();
            return groupList;
        } catch (Exception e) {
            transaction.rollback();
            throw new DaoException("Ошибка получения списка группы ", e);
        }
    }

    public List<Group> findByName(String name) throws DaoException {
        return null;
    }

}
