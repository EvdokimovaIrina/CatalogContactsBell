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
public class DaoGroup extends DaoParsing implements CrudDAO<Group> {
    private ModelMapper<Group> modelMapperGroup;
    private final String selectInsertGroup = "SELECT insertgroup(?,?)";
    private final String selectChangeGroup = "SELECT changegroup(?,?)";
    private final String selectDeleteGroup = "SELECT deletegroup(?)";
    private final String selectGetGroup = "SELECT * FROM getgroup(?)";
    private final String selectGetListGroup = "SELECT * FROM getlistgroup(?,?)";

    public DaoGroup() throws DaoException {
        super();
        modelMapperGroup = new ModelMapperGroup();
    }

    public void create(Group group) throws DaoException {
        saveObgectToBD(group);
    }

    public void update(Group group) throws DaoException {
        executionQuery(selectChangeGroup,group.getNumber(),group.getName());
    }

    public void delete(int number) throws DaoException {
        executionQuery(selectDeleteGroup,number);
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
        return modelMapperGroup.getListOfObjects(executionQuery(selectGetListGroup,
                SecurityContextHolder.getLoggedUserID(),name));
    }

}
