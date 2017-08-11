package catalogContacts.dao.implDAO;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.GetDataFromBD;
import catalogContacts.model.Group;

import java.util.List;

/**
 *
 */
public class DaoGroup implements CrudDAO<Group> {
    public void create(Group object) throws DaoException {

    }

    public void update(Group object) throws DaoException {

    }

    public void delete(int number) throws DaoException {

    }

    public Group getObject(int id) throws DaoException {
        return null;
    }

    public List<Group> getAll() throws DaoException {
        return null;
    }

    public List<Group> findByName(String name) throws DaoException {
        return null;
    }

    public int toFormANewId() throws DaoException {
        return 0;
    }

    public void setUserID(int userID) {

    }

    public void setGetDataFromBD(GetDataFromBD getDataFromBD) {

    }

    public GetDataFromBD getGetDataFromBD() {
        return null;
    }
}
