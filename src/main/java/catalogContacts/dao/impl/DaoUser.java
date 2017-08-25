package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.dao.mappers.impl.ModelMapperUser;
import catalogContacts.dao.mappers.impl.MapperStatisticQuantity;
import catalogContacts.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 *
 */
public class DaoUser extends DaoParsing implements CrudDAOUser<User>{
    private ModelMapper<User> modelMapperUser;
    private ModelMapper<Float> modelMapperQuantity;
    private final String selectAuthorizationUser = "SELECT * FROM authorizationuser(?,?)";
    private final String selectGetUser = "SELECT * FROM getuser(?)";
    private final String selectNumberOfUsers = "SELECT NumberOfUsers()";
    private final String selectAverageUserContact = "SELECT AverageUserContact()";
    private final String selectCountingUserContact = "SELECT * FROM CountingUserContact()";
    private final String selectCountingUserGroup = "SELECT * FROM CountingUserGroup()";
    private final String selectAverageUserGroup = "SELECT AverageUserGroup()";
    private final String selectInactiveUsers = "SELECT * FROM InactiveUsers(?)";

    public DaoUser() throws DaoException {
        super();
        this.modelMapperUser = new ModelMapperUser();
        this.modelMapperQuantity = new MapperStatisticQuantity();
    }

    public void create(User object) throws DaoException {

    }

    public void update(User object) throws DaoException {

    }

    public void delete(int number) throws DaoException {

    }

    public User getObject(int id) throws DaoException {
        return modelMapperUser.getObject(executionQuery(selectGetUser,id));
    }

    public User authorizationUser(String login,String password) throws DaoException {
        User user = new User();

        user.setLogin("test1");
        user.setPassword("111");

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
            try {
                session.beginTransaction().begin();
                session.save(user);
                session.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (session != null) {
                    session.close();
                }
            }

        session.close();
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
        Float floatValue = modelMapperQuantity.getObject(executionQuery(selectNumberOfUsers));
        return floatValue.intValue();
    }

    public float averageUserContact() throws DaoException {
        return modelMapperQuantity.getObject(executionQuery(selectAverageUserContact));
    }

    public float averageUserGroup() throws DaoException {
        return modelMapperQuantity.getObject(executionQuery(selectAverageUserGroup));
    }

    public List<User> inactiveUsers(int n) throws DaoException {
        return modelMapperUser.getListOfObjects(executionQuery(selectInactiveUsers,n));
    }

    public List<User> CountingUserContact() throws DaoException {
        return modelMapperUser.getListOfObjects(executionQuery(selectCountingUserContact));
    }

    public List<User> CountingUserGroup() throws DaoException {
        return modelMapperUser.getListOfObjects(executionQuery(selectCountingUserGroup));
    }

}
