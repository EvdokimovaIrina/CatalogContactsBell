package catalogContacts.dao.implDAO;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.GetDataFromBD;
import catalogContacts.model.Contact;

import java.util.List;

/**
 *
 */
public class DaoContact implements CrudDAO<Contact>{
     private GetDataFromBD getDataFromBD;
    public void create(Contact object) throws DaoException {

    }

    public void update(Contact object) throws DaoException {

    }

    public void delete(int number) throws DaoException {

    }

    public Contact getObject(int id) throws DaoException {
        return null;
    }

    public List<Contact> getAll() throws DaoException {
       return getDataFromBD.getAllContact();
    }

    public List<Contact> findByName(String name) throws DaoException {
        return null;
    }

    public int toFormANewId() throws DaoException {
        return 0;
    }

    public void setGetDataFromBD(GetDataFromBD getDataFromBD) {
        this.getDataFromBD = getDataFromBD;
    }

    public GetDataFromBD getGetDataFromBD() {
        return getDataFromBD;
    }
}
