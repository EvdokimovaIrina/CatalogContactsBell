package catalogContacts.dao.implDAO;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.GetDataFromBD;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.User;

import java.util.List;

/**
 *
 */
public class DaoUser implements CrudDAO<User>{
    public void create(User object) throws DaoException {

    }

    public void update(User object) throws DaoException {

    }

    public void delete(int number) throws DaoException {

    }

    public User getObject(int id) throws DaoException {
        return null;
    }

    public List<User> getAll() throws DaoException {
        return null;
    }

    public List<User> findByName(String name) throws DaoException {
        return null;
    }

    public int toFormANewId() throws DaoException {
        return 0;
    }


}
