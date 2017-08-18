package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.dao.mappers.impl.ModelMapperUser;
import catalogContacts.dao.mappers.impl.StatisticMapperQuantity;
import catalogContacts.model.User;
import java.util.List;

/**
 *
 */
public class DaoUser extends DaoParsing implements CrudDAOUser<User>{
    private ModelMapper<User> modelMapperUser;
    private ModelMapper<Integer> modelMapperQuantity;
    private final String selectAuthorizationUser = "SELECT * FROM authorizationuser(?,?)";
    private final String selectGetUser = "SELECT * FROM getuser(?)";
    private final String selectNumberOfUsers = "SELECT NumberOfUsers()";
    private final String selectAverageUserContact = "SELECT AverageUserContact()";
    private final String selectCountingUserData = "SELECT * FROM CountingUserData()";
    private final String selectAverageUserGroup = "SELECT AverageUserGroup()";
    private final String selectInactiveUsers = "SELECT * FROM InactiveUsers()";

    public DaoUser() throws DaoException {
        super();
        this.modelMapperUser = new ModelMapperUser();
        this.modelMapperQuantity = new StatisticMapperQuantity();
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
        return modelMapperUser.getObject(executionQuery(selectAuthorizationUser,login,password));
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
        return modelMapperQuantity.getObject(executionQuery(selectNumberOfUsers));
    }

}
