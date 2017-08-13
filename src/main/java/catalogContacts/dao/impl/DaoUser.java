package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.dao.mappers.impl.ModelMapperUser;
import catalogContacts.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class DaoUser extends DaoParsing implements CrudDAOUser<User>{
    private ModelMapper<User> modelMapperUser;
    private final String selectAuthorizationUser = "SELECT * FROM authorizationuser(?,?)";
    private final String selectGetUser = "SELECT * FROM getuser(?)";

    public DaoUser() throws DaoException {
        super();
        this.modelMapperUser = new ModelMapperUser();
    }

    public void create(User object) throws DaoException {

    }

    public void update(User object) throws DaoException {

    }

    public void delete(int number) throws DaoException {

    }

    public User getObject(int id) throws DaoException {
        ResultSet result = executionQuery(selectGetUser,id);
        return modelMapperUser.creatObject(result);
    }

    public User authorizationUser(String login,String password) throws DaoException {
        ResultSet result = executionQuery(selectAuthorizationUser,login,password);
        return modelMapperUser.creatObject(result);
    }
    public List<User> getAll() throws DaoException {
        return null;
    }

    public List<User> findByName(String name) throws DaoException {
        return null;
    }

}
