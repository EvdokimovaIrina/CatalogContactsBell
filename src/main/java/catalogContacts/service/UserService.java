package catalogContacts.service;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.User;

/**
 *
 */
public interface UserService {
    void setUserThread(String login,String password) throws DaoException;
    void setUserThread(int id) throws DaoException;
    void setCrudDAOUser(CrudDAOUser<User> crudDAOUser);
    int numberOfUsers() throws DaoException;
}
