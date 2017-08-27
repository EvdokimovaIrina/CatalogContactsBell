package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.dao.mappers.impl.ModelMapperUser;
import catalogContacts.dao.mappers.impl.MapperStatisticQuantity;
import catalogContacts.model.Contact;
import catalogContacts.model.Group;
import catalogContacts.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DaoUser extends DaoGeneral implements CrudDAOUser<User> {
    private final String selectAuthorizationUser = "SELECT * FROM authorizationuser(?,?)";
    private final String selectGetUser = "SELECT * FROM getuser(?)";
    private final String selectNumberOfUsers = "SELECT NumberOfUsers()";
    private final String selectAverageUserContact = "SELECT AverageUserContact()";
    private final String selectCountingUserContact = "SELECT * FROM CountingUserContact()";
    private final String selectCountingUserGroup = "SELECT * FROM CountingUserGroup()";
    private final String selectAverageUserGroup = "SELECT AverageUserGroup()";
    private final String selectInactiveUsers = "FROM InactiveUsers(?)";

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
        return getObjectFromBDById(User.class,id);

    }

    public User authorizationUser(String login, String password) throws DaoException {
        User user=null;
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Criteria userCriteria = session.createCriteria(User.class);
            userCriteria.add(Restrictions.eq("login", login));
            userCriteria.add(Restrictions.eq("password", password));
            user = (User) userCriteria.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
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
        return null;
    }

    public int numberOfUsers() throws DaoException {
        return 0;
    }

    public float averageUserContact() throws DaoException {
        //session.getNamedQuery("friends_online").setParameter("id", 26).list();
        return 0;
    }

    public float averageUserGroup() throws DaoException {
        return 0;
    }

    public List<User> inactiveUsers(int n) throws DaoException {
        List<User> userList=null;
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            userList = (List<User>)session.createSQLQuery("{? = call InactiveUsers (:n)}").setLong("n",n).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DaoException("Ошибка при получении данных ", e);
        }

        return userList;

    }

    public List<Map<User, Integer>> CountingUserContact() throws DaoException {
        return getCountingForUser(Contact.class);
    }

    public List<Map<User, Integer>> CountingUserGroup() throws DaoException {
        return getCountingForUser(Group.class);
    }

    private List<Map<User, Integer>> getCountingForUser(Class clazz) throws DaoException {
        List<Map<User, Integer>> userListMap=null;
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            List<User> userList = (List<User>)session.createQuery("from User").list();
            for (User user: userList) {
                Map<User, Integer> mapForList = new HashMap<>();
                if (clazz == Contact.class) {
                    mapForList.put(user,user.getContactsByUserId().size());
                }else if(clazz== Group.class){
                    mapForList.put(user, user.getGroupsByUserId().size());
                }
                userListMap.add(mapForList);
            }
            transaction.commit();
            return userListMap;
        } catch (Exception e) {
            transaction.rollback();
            throw new DaoException("Ошибка при получении данных ", e);
        }
    }

}
