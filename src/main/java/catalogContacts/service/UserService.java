package catalogContacts.service;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.User;

import java.util.List;

/**
 *
 */
public interface UserService {
    void setUserThread(String login,String password) throws DaoException;
    void setUserThread(int id) throws DaoException;
    void setCrudDAOUser(CrudDAOUser<User> crudDAOUser);
    int numberOfUsers() throws DaoException;
    float averageUserContact() throws DaoException;
    float averageUserGroup() throws DaoException;
    List<User> inactiveUsersList(int n) throws DaoException;
    List<User> countingUserContact() throws DaoException;
    List<User> countingUserGroup() throws DaoException;
}
